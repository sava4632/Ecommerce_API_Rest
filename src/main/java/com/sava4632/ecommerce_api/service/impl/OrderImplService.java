package com.sava4632.ecommerce_api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sava4632.ecommerce_api.model.dao.OrderDao;
import com.sava4632.ecommerce_api.model.dto.OrderDto;
import com.sava4632.ecommerce_api.model.entity.Order;
import com.sava4632.ecommerce_api.service.IOrderService;


@Service
public class OrderImplService implements IOrderService{
    @Autowired
    private OrderDao orderDao;

    /*
     * Retrieves all orders from the database.
     * @return a list of orders.
     */
    @Transactional(readOnly = true)
    @Override
    public List<Order> findAll() {
        return (List<Order>) orderDao.findAll();
    }

    /*
     * Saves or updates an order in the database.
     * @param orderDto the order to save or update.
     * @return the saved or updated order.
     */
    @Transactional
    @Override
    public Order save(OrderDto orderDto) {
        Order order = Order.builder()
                .id(orderDto.getId())
                .userId(orderDto.getUserId())
                .product(orderDto.getProduct())
                .quantity(orderDto.getQuantity())
                .orderDate(orderDto.getOrderDate())
                .build();
        
        return orderDao.save(order);
    }

    /*
     * Retrieves an order from the database by its id.
     * @param id the id of the order to retrieve.
     * @return the order if it exists, null otherwise.
     */
    @Transactional(readOnly = true)
    @Override
    public Order findById(Integer id) {
        return orderDao.findById(id).orElse(null);
    }

    /*
     * Deletes an order from the database.
     */
    @Transactional
    @Override
    public void delete(Order order) {
        orderDao.delete(order);
    }

    /**
     * Checks if an order exists in the database.
     * @param id the id of the order to check.
     * @return true if the order exists, false otherwise.
     */
    @Transactional(readOnly = true)
    @Override
    public boolean existsById(Integer id) {
        return orderDao.existsById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> findAllByUserId(Integer userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllByUserId'");
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> findAllByProductId(Integer productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllByProductId'");
    }
}
