
package com.interventure.task.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author bojana
 */
@AllArgsConstructor
public enum ErrorCode {
    
    BAD_REQUEST(400),
    INTERNAL_ERROR(500);
       
    @Getter private final int value;
    
    
}
