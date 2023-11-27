
package com.interventure.task.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author bojana
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class InternalServiceException extends ProductServiceException{
    
     public InternalServiceException(Throwable ex) {
        super(ErrorCode.INTERNAL_ERROR, ex);

    }

}