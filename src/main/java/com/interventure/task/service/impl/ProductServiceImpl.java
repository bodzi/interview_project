package com.interventure.task.service.impl;

import com.interventure.task.dto.request.CreateProductRequest;
import com.interventure.task.entitiy.Product;
import com.interventure.task.exception.InternalServiceException;
import com.interventure.task.message.Message;
import com.interventure.task.message.Message.Action;
import com.interventure.task.repository.ProductRepository;
import com.interventure.task.service.KafkaProducerService;
import com.interventure.task.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 *
 * @author bojana
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final KafkaProducerService kafkaProducer;

    @Override
    public long createProduct(CreateProductRequest productRequest) throws InternalServiceException {

        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .build();

 
        try {
            product = productRepository.save(product);
            kafkaProducer.send(new Message(Action.CREATED,product));
            log.info("Product created {}", product.getProductId());
            return product.getProductId();
        } catch (Throwable ex) {
            throw new InternalServiceException("Internal error", ex);
        }

    }


}
