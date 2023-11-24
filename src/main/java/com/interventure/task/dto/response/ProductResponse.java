
package com.interventure.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author bojana
 */


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    
    private String productName;
    private long productId;
    private long price;
    
}
