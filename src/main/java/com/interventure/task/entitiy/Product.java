
package com.interventure.task.entitiy;

import jakarta.persistence.*;
import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author bojana
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Valid
    private long productId;

    @Column(nullable = false, unique = true, name = "NAME")
    @Valid
    private String name;

    @Column(nullable = false,name = "PRICE")
    @Valid
    private double price;
    
}
