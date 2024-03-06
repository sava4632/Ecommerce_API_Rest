package com.sava4632.ecommerce_api.service;

import java.util.List;

import com.sava4632.ecommerce_api.model.dto.ProductDto;
import com.sava4632.ecommerce_api.model.entity.Product;

public interface IProductService {
     
    /*
     * Find all the products and return a list of products.
     */
    List<Product> findAll();

    /*
     * Save the product to the database and return the saved product.
     */
    Product save(ProductDto productDto);

    /*
     * Find the product by id and return the product.
     */
    Product findById(Integer id);

    /*
     * Delete the product from the database.
     */
    void delete(Product product);

    /*
     * Check if the product exists by id and return a boolean.
     */
    boolean existsById(Integer id);
}
