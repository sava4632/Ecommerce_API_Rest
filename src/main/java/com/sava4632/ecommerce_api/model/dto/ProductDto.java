package com.sava4632.ecommerce_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * ProductDto is a Data Transfer Object that is used to transfer data between the service layer and the controller layer.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductDto {
    private Integer id;
    private String name;
    private double price;
    private String description;
}
