package com.interventure.task.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author bojana
 */
@Data
@Builder
public class CreateProductRequest {

    @NotNull(message = "Name is required.")
    private String name;
    @NotNull(message = "Price is required.")
    @PositiveOrZero(message = "Price must be negative")
    private double price;

}
