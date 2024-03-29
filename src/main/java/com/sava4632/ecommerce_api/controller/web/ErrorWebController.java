package com.sava4632.ecommerce_api.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorWebController {
    /**
     * This endpoint is used to handle 404 error page
     * @return 404 error page
     */
    @GetMapping("/error")
    public String handleError() {
        return "error/404"; // Returns 404 error page
    }

    /**
     * This endpoint is used to handle all other URLs except the ones defined in the controllers
     * @return 404 error page
     */
    @RequestMapping("*")
    public String fallback() {
        return "redirect:/error"; // Redirects to 404 error page if any other URL is entered    
    }
}
