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

import com.sava4632.ecommerce_api.model.dto.ProductDto;
import com.sava4632.ecommerce_api.model.entity.Product;
import com.sava4632.ecommerce_api.model.payload.MessageResponse;
import com.sava4632.ecommerce_api.service.impl.ProductImplService;


@RestController
@RequestMapping("api/v1/")
public class ProductController {

    @Autowired
    private ProductImplService productService;

    /*
     * This endpoint is used to get all products from the database.
     */
    @GetMapping("products")
    public ResponseEntity<?> showAll(){
        List<Product> getList = productService.findAll();

        if (getList == null || getList.isEmpty()) {
            return new ResponseEntity<>(MessageResponse.builder()
                .message("There are no records")
                .data(null)
                .build()
            , HttpStatus.OK);
            
        }

        return new ResponseEntity<>(MessageResponse.builder()
                .message("Data found")
                .data(getList)
                .build()
            , HttpStatus.OK);
    }

    /*
     * This endpoint is used to create a new product in the database.
     */
    @PostMapping("product")
    public ResponseEntity<?> create (@RequestBody ProductDto productDto){
        Product productSave = null;
        try {
            productSave = productService.save(productDto);

            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Product created successfully")
                    .data(ProductDto.builder()
                        .id(productSave.getId())
                        .name(productSave.getName())
                        .price(productSave.getPrice())
                        .description(productSave.getDescription())
                        .build())
                    .build()
                , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(exDt.getMessage())
                    .data(null)
                    .build()
                , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * This endpoint is used to update a product in the database.
     */
    @PutMapping("product/{id}")
    public ResponseEntity<?> update (@RequestBody ProductDto productDto, @PathVariable Integer id){
        Product productUpdate = null;
        try {

            if (!productService.existsById(id)) { // Check if the product exists by id
                return new ResponseEntity<>(MessageResponse.builder()
                    .message("Product not found")
                    .data(null)
                    .build(), HttpStatus.NOT_FOUND);
            }

            productDto.setId(id);
            productUpdate = productService.save(productDto);

            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Product created successfully")
                    .data(ProductDto.builder()
                        .id(productUpdate.getId())
                        .name(productUpdate.getName())
                        .price(productUpdate.getPrice())
                        .description(productUpdate.getDescription())
                        .build())
                    .build()
                , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(exDt.getMessage())
                    .data(null)
                    .build()
                , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * This endpoint is used to delete a product in the database.
     */
    @DeleteMapping("product/{id}")
    public ResponseEntity<?> delete (@PathVariable Integer id){
        try {
            Product productDelete = productService.findById(id);

            if (productDelete == null) {
                return new ResponseEntity<>(MessageResponse.builder()
                    .message("Product not found")
                    .data(null)
                    .build()
                , HttpStatus.OK);
            }

            productService.delete(productDelete);

            return new ResponseEntity<>(MessageResponse.builder()
                .message("Product deleted successfully")
                .data(null)
                .build()
            , HttpStatus.NO_CONTENT);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(exDt.getMessage())
                    .data(null)
                , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * This endpoint is used to get a product from the database by its id.
     */
    @GetMapping("product/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id){
        Product product = productService.findById(id);

        if (product == null) {
            return new ResponseEntity<>(MessageResponse.builder()
                .message("Product not found")
                .data(null)
                .build()
            , HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(MessageResponse.builder()
                .message("Product found")
                .data(ProductDto.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .description(product.getDescription())
                    .build())
                .build()
            , HttpStatus.OK);
    }
}
