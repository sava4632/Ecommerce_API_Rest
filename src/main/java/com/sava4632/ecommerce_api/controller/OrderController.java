package com.sava4632.ecommerce_api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sava4632.ecommerce_api.model.dto.OrderDto;
import com.sava4632.ecommerce_api.model.entity.Order;
import com.sava4632.ecommerce_api.model.payload.MessageResponse;
import com.sava4632.ecommerce_api.service.impl.OrderImplService;
import com.sava4632.ecommerce_api.service.impl.ProductImplService;
import com.sava4632.ecommerce_api.service.impl.UserImplService;

@RestController
@RequestMapping("api/v1/")
public class OrderController {

    @Autowired
    private OrderImplService orderService;
    @Autowired
    private ProductImplService productService;
    @Autowired
    private UserImplService userService;

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
    public ResponseEntity<?> create(@RequestParam("userId") Integer userId, @RequestParam("productIds") List<Integer> productIds, @RequestParam("quantities") List<Integer> quantities){
        List<OrderDto> ordersSaved = new ArrayList<>();
        try {
            // Check if the user exists in the database.
            if (!userService.existsById(userId)) {
                return new ResponseEntity<>(MessageResponse.builder()
                    .message("User not found")
                    .data(null)
                    .build()
                , HttpStatus.NOT_FOUND);
            }

            for (Integer product : productIds) {
                // Check if the products exist in the database.
                if (!productService.existsById(product)) {
                    return new ResponseEntity<>(MessageResponse.builder()
                        .message("Product " + product + "not found")
                        .data(null)
                        .build()
                    , HttpStatus.NOT_FOUND);
                }
                OrderDto orderDto = OrderDto.builder()
                        .userId(userId)
                        .product(productService.findById(product))
                        .quantity(quantities.get(productIds.indexOf(product)))
                        .build();

                Order orderSave = orderService.save(orderDto);
                ordersSaved.add(OrderDto.builder()
                        .id(orderSave.getId())
                        .userId(orderSave.getUserId())
                        .product(orderSave.getProduct())
                        .quantity(orderSave.getQuantity())
                        .orderDate(orderSave.getOrderDate())
                        .build());
            }

            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Orders created successfully")
                    .data(ordersSaved)
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
