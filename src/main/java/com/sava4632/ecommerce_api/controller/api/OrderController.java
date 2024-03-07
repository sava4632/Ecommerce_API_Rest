package com.sava4632.ecommerce_api.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sava4632.ecommerce_api.model.dto.OrderDto;
import com.sava4632.ecommerce_api.model.entity.Order;
import com.sava4632.ecommerce_api.model.entity.Product;
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

        List<OrderDto> getListDto = new ArrayList<>();
        for(Order order : getList){ // Add the orders to the list of orders dto to be returned.
            getListDto.add(OrderDto.builder()
                    .id(order.getId())
                    .userId(order.getUserId())
                    .product(order.getProduct())
                    .quantity(order.getQuantity())
                    .orderDate(order.getOrderDate())
                    .build());
        }

        return new ResponseEntity<>(MessageResponse.builder()
                .message("Orders found")
                .data(getListDto)
                .build()
            , HttpStatus.OK);
    }

    /*
     * This endpoint creates multiple orders in the database.
     * The user id, product ids, and quantities are passed as request parameters.
     */
    @PostMapping("order")
    public ResponseEntity<?> create(@RequestParam("userId") Integer userId, @RequestParam("productIds") List<Integer> productIds, 
        @RequestParam("quantities") List<Integer> quantities){
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

    /*
     * This endpoint updates an order in the database.
     * The order id, user id, product id, and quantity are passed as request parameters.
     */
    @PutMapping("order/{orderId}")
    public ResponseEntity<?> update(@PathVariable Integer orderId, @RequestParam("userId") Integer userId, @RequestParam("productId") 
        Integer productId, @RequestParam("quantity") Integer quantity){
        
        Order orderUpdate = null;
        try {
            Order order = orderService.findById(orderId);
            Product product = productService.findById(productId);
            // Check if the order exists in the database.
            if (order == null) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Order not found")
                        .data(null)
                        .build()
                    , HttpStatus.NOT_FOUND);
            }else if (order.getUserId() != userId) { // Check if the order is associated with the user.
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Order not associated with user")
                        .data(null)
                        .build()
                    , HttpStatus.NOT_FOUND);
                
            }else if (product == null){ // Check if the product exists in the database.
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Product not found")
                        .data(null)
                        .build()
                    , HttpStatus.NOT_FOUND);
            }
            
            // Update the order in the database.
            orderUpdate = orderService.save(OrderDto.builder()
                    .id(orderId)
                    .userId(userId)
                    .product(product)
                    .quantity(quantity)
                    .build());
            
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Order updated successfully")
                    .data(OrderDto.builder()
                        .id(orderUpdate.getId())
                        .userId(orderUpdate.getUserId())
                        .product(orderUpdate.getProduct())
                        .quantity(orderUpdate.getQuantity())
                        .orderDate(orderUpdate.getOrderDate())
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

    /*
     * This endpoint deletes an order from the database.
     * The order id and user id are passed as request parameters.
     */
    @DeleteMapping("order/{orderId}")
    public ResponseEntity<?> delete(@PathVariable Integer orderId, @RequestParam("userId") Integer userId){
        try {
            Order orderDelete = orderService.findById(orderId);
            if (orderDelete == null) { // Check if the order exists in the database.
                return new ResponseEntity<>(MessageResponse.builder()
                    .message("Order not found")
                    .data(null)
                    .build()
                , HttpStatus.NOT_FOUND);
            }else if (orderDelete.getUserId() != userId) { // Check if the order is associated with the user.
                return new ResponseEntity<>(MessageResponse.builder()
                    .message("Order not associated with user")
                    .data(null)
                    .build()
                , HttpStatus.NOT_FOUND);
            }

            orderService.delete(orderDelete);

            return new ResponseEntity<>(MessageResponse.builder()
                        .message("Order deleted successfully ")
                        .data(null)
                        .build()
                    , HttpStatus.NO_CONTENT);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(exDt.getMessage())
                    .data(null)
                    .build()
                , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * This endpoint retrieves all orders from the database by user id.
     * The user id is passed as a request parameter.
     */
    @GetMapping("orders/user/{userId}")
    public ResponseEntity<?> showAllByUserId(@PathVariable Integer userId){
        List<Order> getList = orderService.findAllByUserId(userId);

        if (getList == null || getList.isEmpty()) { // Check if the orders exist in the database.
            return new ResponseEntity<>(MessageResponse.builder()
                .message("There are no records")
                .data(null)
                .build() 
            , HttpStatus.OK);
        }

        List<OrderDto> getListDto = new ArrayList<>();
        for(Order order : getList){ // Add the orders to the list of orders dto to be returned.
            getListDto.add(OrderDto.builder()
                    .id(order.getId())
                    .userId(order.getUserId())
                    .product(order.getProduct())
                    .quantity(order.getQuantity())
                    .orderDate(order.getOrderDate())
                    .build());
        }

        return new ResponseEntity<>(MessageResponse.builder()
                .message("Orders found")
                .data(getListDto)
                .build()
            , HttpStatus.OK);
    }

    @GetMapping("order/{orderId}")
    public ResponseEntity<?> showById(@PathVariable Integer orderId, @RequestParam("userId") Integer userId){
        Order order = orderService.findById(orderId);

        if (order == null) { // Check if the order exists in the database.
            return new ResponseEntity<>(MessageResponse.builder()
                .message("Order not found")
                .data(null)
                .build()
            , HttpStatus.NOT_FOUND);
        }else if (order.getUserId() != userId) { // Check if the order is associated with the user.
            return new ResponseEntity<>(MessageResponse.builder()
                .message("Order not associated with user")
                .data(null)
                .build()
            , HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(MessageResponse.builder()
                .message("Order found")
                .data(OrderDto.builder()
                    .id(order.getId())
                    .userId(order.getUserId())
                    .product(order.getProduct())
                    .quantity(order.getQuantity())
                    .orderDate(order.getOrderDate())
                    .build())
                .build()
            , HttpStatus.OK);
    }
}
