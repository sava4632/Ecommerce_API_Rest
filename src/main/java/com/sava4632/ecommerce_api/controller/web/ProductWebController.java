package com.sava4632.ecommerce_api.controller.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sava4632.ecommerce_api.model.dto.ProductDto;
import com.sava4632.ecommerce_api.model.entity.Product;
import com.sava4632.ecommerce_api.service.impl.ProductImplService;

@Controller
@RequestMapping("/products")
public class ProductWebController {

    @Autowired
    private ProductImplService productService;

    @GetMapping({"/", ""})
    public String showProducts(Model model) { // model is a container for the data to be displayed in the view.
        List<Product> productList = productService.findAll(); 
        List<ProductDto> productListDto = new ArrayList<>();
        
        // change the list of products to a list of productDtos
        for (Product product : productList) {
            productListDto.add(ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build());
        }
        // add the list of productDtos to the model to be displayed in the view.
        model.addAttribute("products", productListDto); 

        return "products"; // this is the name of the view to be displayed.
    }
}
