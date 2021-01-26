package com.benz.redis.api.dao;

import com.benz.redis.api.model.Product;

import java.util.Map;
import java.util.Optional;

public interface ProductDAO {

    Optional<Product> findProduct(int productId);
    Optional<Map<Integer,Product>> findAllProducts();
    Product saveProduct(Product product);
    Optional<Product> updateProduct(Product product);
    void deleteProduct(int productId);
}
