package com.interventure.task.controller;

import com.interventure.task.dto.request.CreateProductRequest;
import com.interventure.task.exception.ErrorCode;
import com.interventure.task.exception.InternalServiceException;
import com.interventure.task.exception.ProductServiceException;
import com.interventure.task.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 *
 * @author bojana
 */
class ProductControllerTest {

    private ProductService productServiceMock;
    private ProductController productController;
    private CreateProductRequest productRequest;

    @BeforeEach
    public void setUp() {

        productServiceMock = mock(ProductService.class);
        productController = new ProductController(productServiceMock);

        productRequest = CreateProductRequest.builder().name("TestProduct").price(3).build();

    }

    @Test
    void addProduct_successful() throws ProductServiceException {

        when(productServiceMock.createProduct(any(CreateProductRequest.class))).thenReturn(1L);

        ResponseEntity<Long> responseEntity = productController.addProduct(productRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody());
        verify(productServiceMock, times(1)).createProduct(any(CreateProductRequest.class));
    }

    @Test
    void addProduct_InternalServiceException() throws ProductServiceException {

        when(productServiceMock.createProduct(any(CreateProductRequest.class))).thenThrow(new InternalServiceException(new Exception("Test Exception")));

        InternalServiceException exception = assertThrows(
                InternalServiceException.class, ()
                -> productController.addProduct(productRequest)
        );

        assertEquals("500: Test Exception", exception.getMessage());
        verify(productServiceMock, times(1)).createProduct(any(CreateProductRequest.class));
    }
    
     @Test
    void addProduct_ProductServiceException() throws ProductServiceException {

        when(productServiceMock.createProduct(any(CreateProductRequest.class))).thenThrow(new ProductServiceException("Test Exception" ,ErrorCode.BAD_REQUEST));

        ProductServiceException exception = assertThrows(
                ProductServiceException.class, ()
                -> productController.addProduct(productRequest)
        );

        assertEquals("400: Test Exception", exception.getMessage());
        verify(productServiceMock, times(1)).createProduct(any(CreateProductRequest.class));
    }
}
