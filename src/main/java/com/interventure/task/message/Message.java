
package com.interventure.task.message;

import com.interventure.task.entitiy.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author bojana
 */
@Data
@AllArgsConstructor
public class Message {
    
    public enum Action{
    CREATED,
    UPDATED
    }
    
    private Action action;
    private Product product;
       
}
