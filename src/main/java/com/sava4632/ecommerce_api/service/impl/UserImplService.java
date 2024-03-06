package com.sava4632.ecommerce_api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sava4632.ecommerce_api.model.dao.UserDao;
import com.sava4632.ecommerce_api.model.dto.UserDto;
import com.sava4632.ecommerce_api.model.entity.User;
import com.sava4632.ecommerce_api.service.IUserService;

@Service
public class UserImplService implements IUserService{

    @Autowired // This is a dependency injection.
    private UserDao userDao;

    /*
     * Saves or updates a user in the database.
     * @param userDto the user to save or update.
     * @return the saved or updated user.
     */
    @Transactional // This is a transaction.
    @Override
    public User save(UserDto userDto) {
        User user = User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .age(userDto.getAge())
                .build();
        return userDao.save(user);
    }

    /*
     * Retrieves a user from the database by its id.
     * @param id the id of the user to retrieve.
     * @return the user with the given id, or null if no such user exists.
     */
    @Transactional(readOnly = true) // This is a read-only transaction.
    @Override
    public User findById(Integer id) {
        return userDao.findById(id).orElse(null); // This is a null check.
    }

    /*
     * Updates a user in the database.
     * @param user the user to update.
     * @return the updated user.
     */
    @Transactional
    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    /*
     * Checks if a user exists in the database.
     * @param id the id of the user to check.
     * @return true if the user exists, false otherwise.
     */
    @Transactional(readOnly = true)
    @Override
    public boolean existsById(Integer id) {
        return userDao.existsById(id);
    }


    /*
    * Retrieves all users from the database.
    *
    * @return a list of User objects representing all users in the database.
    */
    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return (List<User>) userDao.findAll();
    }
}
