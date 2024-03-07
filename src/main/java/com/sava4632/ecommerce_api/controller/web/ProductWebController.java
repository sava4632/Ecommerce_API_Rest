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
@RequestMapping("web/v1/")
public class ProductWebController {

    @Autowired
    private ProductImplService productService;

    @GetMapping("products")
    public String showProducts(Model model) {
        // Obtener la lista de productos de la base de datos
        List<Product> productList = productService.findAll(); // Asegúrate de implementar este método
        List<ProductDto> productListDto = new ArrayList<>();
        
        for (Product product : productList) {
            productListDto.add(ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build());
        }
        // Pasar la lista de productos al modelo
        model.addAttribute("products", productListDto);

        // Devolver el nombre de la vista (template) que se utilizará para renderizar la página web
        return "products";
    }
}
