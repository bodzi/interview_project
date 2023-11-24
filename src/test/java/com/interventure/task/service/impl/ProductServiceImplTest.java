package com.interventure.task.service.impl;

import com.interventure.task.dto.request.CreateProductRequest;
import com.interventure.task.entitiy.Product;
import com.interventure.task.exception.InternalServiceException;
import com.interventure.task.message.Message;
import com.interventure.task.repository.ProductRepository;
import com.interventure.task.service.KafkaProducerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author bojana
 */
public class ProductServiceImplTest {

    private ProductRepository productRepositoryMock;
    private KafkaProducerService kafkaProducerMock;
    private ProductServiceImpl productService;
    private CreateProductRequest productRequest;

    @BeforeEach
    public void setUp() {
        productRepositoryMock = mock(ProductRepository.class);
        kafkaProducerMock = mock(KafkaProducerService.class);
        productService = new ProductServiceImpl(productRepositoryMock, kafkaProducerMock);

        productRequest = CreateProductRequest.builder().name("TestProduct").price(3.0).build();

    }

    /**
     * Test of createProduct method, of class ProductServiceImpl.
     */
    @Test
    public void createProduct_successful() throws InternalServiceException {
        System.out.println("createProduct");

        long expResult = 0L;

        Product productMock = mock(Product.class);

        when(productRepositoryMock.save(any(Product.class))).thenReturn(productMock);
        doNothing().when(kafkaProducerMock).send(any(Message.class));
        when(productMock.getProductId()).thenReturn(expResult);

        long result = productService.createProduct(productRequest);
        assertEquals(expResult, result);
        verify(productRepositoryMock, times(1)).save(any(Product.class));
        verify(kafkaProducerMock, times(1)).send(any(Message.class));

    }

    @Test()
    public void createProduct_exception() {
        System.out.println("createProduct");

        when(productRepositoryMock.save(any(Product.class))).thenThrow(IllegalArgumentException.class);

        InternalServiceException exception = assertThrows(InternalServiceException.class, ()
                -> productService.createProduct(productRequest));

        String expectedMessage = "Internal error";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
