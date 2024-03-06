package com.sava4632.ecommerce_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sava4632.ecommerce_api.model.dto.OrderDto;
import com.sava4632.ecommerce_api.model.entity.Order;
import com.sava4632.ecommerce_api.model.payload.MessageResponse;
import com.sava4632.ecommerce_api.service.impl.OrderImplService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("api/v1/")
public class OrderController {

    @Autowired
    private OrderImplService orderService;

    /*
     * This endpoint retrieves all orders from the database.
     */
    @GetMapping("orders")
    public ResponseEntity<?> showAll(){
        List<Order> getList = orderService.findAll();

        if (getList == null || getList.isEmpty()) {
            return new ResponseEntity<>(MessageResponse.builder()
                .message("There are no records")
                .data(null)
                .build() 
            , HttpStatus.OK);
        }

        return new ResponseEntity<>(MessageResponse.builder()
                .message("Orders found")
                .data(getList)
                .build()
            , HttpStatus.OK);
    }

    @PostMapping("order")
    public ResponseEntity<?> create(@RequestBody OrderDto orderDto){
        Order orderSave = null;
        try {
            orderSave = orderService.save(orderDto);

            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Order created successfully")
                    .data(OrderDto.builder()
                        .id(orderSave.getId())
                        .user(orderSave.getUser())
                        .product(orderSave.getProduct())
                        .quantity(orderSave.getQuantity())
                        .orderDate(orderSave.getOrderDate())
                        .build())
                    .build()
                , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(exDt.getMessage())
                    .data(null)
                    .build()
                , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
