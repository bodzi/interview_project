
package com.interventure.task.service;

import com.interventure.task.dto.request.CreateProductRequest;
import com.interventure.task.exception.InternalServiceException;


/**
 *
 * @author bojana
 */
public interface ProductService {
       
    long createProduct(CreateProductRequest productRequest) throws InternalServiceException;

    
}
