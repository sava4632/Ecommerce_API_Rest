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
                .user(orderDto.getUser())
                .product(orderDto.getProduct())
                .quantity(orderDto.getQuantity())
                .orderDate(orderDto.getOrderDate())
                .build();
        
        return orderDao.save(order);
    }

    @Transactional(readOnly = true)
    @Override
    public Order findById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Transactional
    @Override
    public void delete(OrderDto orderDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsById'");
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
