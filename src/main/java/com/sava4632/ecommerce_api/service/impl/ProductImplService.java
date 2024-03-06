package com.sava4632.ecommerce_api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sava4632.ecommerce_api.model.dao.ProductDao;
import com.sava4632.ecommerce_api.model.dto.ProductDto;
import com.sava4632.ecommerce_api.model.entity.Product;
import com.sava4632.ecommerce_api.service.IProductService;

@Service
public class ProductImplService implements IProductService{

    @Autowired
    private ProductDao  productDao; // This is a repository.

    /*
     * Retrieves all products from the database.
     */
    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return (List<Product>) productDao.findAll();
    }

    /*
     * Saves or updates a product in the database.
     * @param productDto the product to save or update.
     * @return the saved or updated product.
     */
    @Transactional
    @Override
    public Product save(ProductDto productDto) {
        Product product = Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .build();

        return productDao.save(product);
    }

    /*
     * Retrieves a product from the database by its id.
     * @param id the id of the product to retrieve.
     * @return the product with the given id, or null if no such product exists.
     */
    @Transactional(readOnly = true)
    @Override
    public Product findById(Integer id) {
        return productDao.findById(id).orElse(null); // This is a null check.
    }

    /*
     * Deletes a product from the database.
     * @param product the product to delete.
     */
    @Transactional
    @Override
    public void delete(Product product) {
        productDao.delete(product);
    }

    /*
     * Checks if a product exists in the database.
     * @param id the id of the product to check.
     * @return true if the product exists, false otherwise.
     */
    @Transactional(readOnly = true)
    @Override
    public boolean existsById(Integer id) {
        return productDao.existsById(id);
    }

}
