package com.sava4632.ecommerce_api.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sava4632.ecommerce_api.model.dto.UserDto;
import com.sava4632.ecommerce_api.model.entity.User;
import com.sava4632.ecommerce_api.model.payload.MessageResponse;
import com.sava4632.ecommerce_api.service.IUserService;

@RestController // This annotation is used to create RESTful web services using Spring MVC
@RequestMapping("/api/v1/") // This annotation is used to map web requests onto specific handler classes and/or handler methods
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("users")
    public ResponseEntity<?> showAll() {
        List<User> getList = userService.findAll();

        if(getList == null || getList.isEmpty()){
            return new ResponseEntity<>(MessageResponse.builder()
                .message("There are no records")
                .data(null)
                .build(), HttpStatus.OK);
        }

        return new ResponseEntity<>(MessageResponse.builder()
                .message("Data found")
                .data(getList)
                .build(), HttpStatus.OK);
    }

    @PostMapping("user")
    public ResponseEntity<?> create(@RequestBody UserDto userDto) { // This annotation is used to bind the HTTP request/response body with a domain object in method parameter or return type
        User userSave = null;
        try{
            userSave = userService.save(userDto);

            return new ResponseEntity<>(MessageResponse.builder()
                .message("User created successfully")
                .data( UserDto.builder()
                    .id(userSave.getId())
                    .name(userSave.getName())
                    .email(userSave.getEmail())
                    .age(userSave.getAge())
                    .registrationDate(userSave.getRegistrationDate())
                    .build())
                .build(), HttpStatus.CREATED);
        }catch(DataAccessException exDt){
            return new ResponseEntity<>(MessageResponse.builder()
                .message(exDt.getMessage())
                .data(null)
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("user/{id}")
    public ResponseEntity<?> update(@RequestBody UserDto userDto, @PathVariable Integer id) {
        User userUpdate = null;
        try{
            if(!userService.existsById(id)){ // Check if the user exists by id
                return new ResponseEntity<>(MessageResponse.builder()
                    .message("User not found")
                    .data(null)
                    .build(), HttpStatus.NOT_FOUND);
            }

            userDto.setId(id);
            userUpdate = userService.save(userDto);

            return new ResponseEntity<>(MessageResponse.builder()
                .message("User updated successfully")
                .data( UserDto.builder()
                    .id(userUpdate.getId())
                    .name(userUpdate.getName())
                    .email(userUpdate.getEmail())
                    .age(userUpdate.getAge())
                    .registrationDate(userUpdate.getRegistrationDate())
                    .build())
                .build(), HttpStatus.CREATED);
        }catch(DataAccessException exDt){
            return new ResponseEntity<>(MessageResponse.builder()
                .message(exDt.getMessage())
                .data(null)
                .build()
                , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) { // This annotation is used to bind the path variable to a method parameter
        try{
            User userDelete = userService.findById(id); // Search for the user to delete

            if(userDelete == null){
                return new ResponseEntity<>(MessageResponse.builder()
                    .message("User not found")
                    .data(null)
                    .build(), HttpStatus.NOT_FOUND);
            }
            
            userService.delete(userDelete);
            return new ResponseEntity<>(MessageResponse.builder()
                .message("User deleted successfully")
                .data(null)
                .build(), HttpStatus.NO_CONTENT);
        }catch(DataAccessException exDt){ // This exception is thrown when an error occurs while accessing data from the database
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(exDt.getMessage())
                    .data(null)
                    .build()
                , HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        User user = userService.findById(id);

        if(user == null){
            return new ResponseEntity<>(MessageResponse.builder()
                .message("User not found")
                .data(null)
                .build(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(MessageResponse.builder()
                .message("User found")
                .data(UserDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .age(user.getAge())
                    .registrationDate(user.getRegistrationDate())
                    .build())
                .build(), HttpStatus.OK);
    }
}
