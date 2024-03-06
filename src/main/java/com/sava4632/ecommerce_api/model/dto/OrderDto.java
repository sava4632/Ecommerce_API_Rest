package com.sava4632.ecommerce_api.model.dto;

import java.util.Date;

import com.sava4632.ecommerce_api.model.entity.Product;
import com.sava4632.ecommerce_api.model.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderDto {
    private Integer id;
    private User user;
    private Product product;
    private Integer quantity;
    private Date orderDate;
}
