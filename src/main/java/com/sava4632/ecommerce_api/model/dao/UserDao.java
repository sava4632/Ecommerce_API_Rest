package com.sava4632.ecommerce_api.model.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sava4632.ecommerce_api.model.entity.User;

/*
 * This is the DAO class for the User entity. It extends the CrudRepository interface which provides basic CRUD operations.
 */
@Repository
public interface UserDao extends CrudRepository<User, Integer>{
    
}
