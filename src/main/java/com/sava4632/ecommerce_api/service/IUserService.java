package com.sava4632.ecommerce_api.service;


import java.util.List;
import com.sava4632.ecommerce_api.model.dto.UserDto;
import com.sava4632.ecommerce_api.model.entity.User;

public interface IUserService {

    /*
     * Find all the users and return a list of users.
     */
    List<User> findAll();

    /*
     * Save the user to the database and return the saved user.
     */
    User save(UserDto userDto);

    /*
     * Find the user by id and return the user.
     */
    User findById(Integer id);

    /*
     * Delete the user from the database.
     */
    void delete(User user);

    /*
     * Check if the user exists by id and return a boolean.
     */
    boolean existsById(Integer id);

}
