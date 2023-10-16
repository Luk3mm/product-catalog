package com.product.catalog.tests;

import com.product.catalog.dto.ProductDTO;
import com.product.catalog.entities.Category;
import com.product.catalog.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct(){
        Product product = new Product(1L, "Phone", "Good Phone" , 800.0, "https://img.com/img.png", Instant.parse("2020-10-20T03:00:00z"));
        product.getCategories().add(new Category(1L, "Books"));
        return product;
    }

    public static ProductDTO createProductDTO(){
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }

}
