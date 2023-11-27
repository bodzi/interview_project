
package com.interventure.task.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author bojana
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ProductServiceException extends Exception{

    private ErrorCode errorCode;

    public ProductServiceException(String message, ErrorCode errorCode) {
        super(errorCode.getValue()+": "+message);
        this.errorCode = errorCode;
    }
    
    public ProductServiceException( ErrorCode errorCode, Throwable th) {
        super(errorCode.getValue()+": "+th.getMessage(), th);
        this.errorCode = errorCode;
    }
}