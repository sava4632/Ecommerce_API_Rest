package com.sava4632.ecommerce_api.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeWebController {
    /**
     * This endpoint is used to show the index page
     * @return index page
     */
    @GetMapping({"", "index"})
    public String showIndex() {
        return "index";
    }
}
