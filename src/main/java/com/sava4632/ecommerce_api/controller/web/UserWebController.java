package com.sava4632.ecommerce_api.controller.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sava4632.ecommerce_api.model.dto.UserDto;
import com.sava4632.ecommerce_api.model.entity.User;
import com.sava4632.ecommerce_api.service.impl.UserImplService;


@Controller
@RequestMapping("/users")
public class UserWebController {
    @Autowired
    private UserImplService userService;

    @GetMapping({"/", ""})
    public String showUsers(Model model) {
        List<User> userList = userService.findAll();
        List<UserDto> userListDto = new ArrayList<>();

        for (User user : userList) {
            userListDto.add(UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .age(user.getAge())
            .registrationDate(user.getRegistrationDate())
            .build());
        }

        model.addAttribute("users", userListDto);
        return "users";
    }
}
