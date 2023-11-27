package com.interventure.task.controller;

import com.interventure.task.dto.request.CreateProductRequest;
import com.interventure.task.exception.InternalServiceException;
import com.interventure.task.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author bojana
 */
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> addProduct (@Valid @RequestBody CreateProductRequest request) throws InternalServiceException {          
        ThreadContext.put("request-body", request.toString());
        
        log.debug("add product called");
        long productId = productService.createProduct(request); 
        
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

}
