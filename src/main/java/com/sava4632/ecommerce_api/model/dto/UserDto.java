package com.sava4632.ecommerce_api.model.dto;

import java.io.Serializable;
import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * UserDto is a Data Transfer Object that is used to transfer data between the service layer and the controller layer.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder // This annotation is used to create a builder class that simplifies the creation of objects with many attributes
public class UserDto implements Serializable{
    private Integer id;
    private String name;
    private String email;
    private Integer age;
    private Date registrationDate;
}
