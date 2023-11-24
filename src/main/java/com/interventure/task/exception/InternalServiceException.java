
package com.interventure.task.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author bojana
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class InternalServiceException extends Exception{

    public InternalServiceException(String message) {
        super(message);
    }
    
    public InternalServiceException(String message, Throwable th) {
        super(message, th);
    }
}