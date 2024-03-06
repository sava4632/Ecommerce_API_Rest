package com.sava4632.ecommerce_api.model.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sava4632.ecommerce_api.model.entity.Order;

/*
 * This is the DAO class for Order entity which extends CrudRepository
 */
@Repository
public interface OrderDao extends CrudRepository<Order, Integer>{

}
