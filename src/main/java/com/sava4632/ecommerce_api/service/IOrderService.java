package com.sava4632.ecommerce_api.service;

import java.util.List;

import com.sava4632.ecommerce_api.model.dto.OrderDto;
import com.sava4632.ecommerce_api.model.entity.Order;

public interface IOrderService {
    /*
     * Find all the orders and return a list of orders.
     */
    List<Order> findAll();

    /*
     * Save or update the order to the database and return the saved or updated order.
     */
    Order save(OrderDto orderDto);

    /*
     * Find the order by id and return the order.
     */
    Order findById(Integer id);

    /*
     * Delete the order from the database.
     */
    void delete(Order order);

    /*
     * Check if the order exists by id and return a boolean.
     */
    boolean existsById(Integer id);

    /*
     * Find all the orders by user id and return a list of orders.
     */
    List<Order> findAllByUserId(Integer userId);
}
