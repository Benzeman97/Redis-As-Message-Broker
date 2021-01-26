package com.benz.redis.api.dao.impl;

import com.benz.redis.api.dao.ProductDAO;
import com.benz.redis.api.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class ProductDAOImpl implements ProductDAO {

    final private static String HASH_KEY="Product";

   final private static Logger LOGGER= LogManager.getLogger(ProductDAOImpl.class);

    private HashOperations<String,Integer,Product> hashOperations;

    @Autowired
    public ProductDAOImpl(RedisTemplate<String,Product> redisTemplate)
    {
        this.hashOperations=redisTemplate.opsForHash();
    }

    @Override
    public Optional<Product> findProduct(int productId) {
        LOGGER.info("call the product from DB");
        return Optional.of(hashOperations.get(HASH_KEY,productId));
    }

    @Override
    public Optional<Map<Integer, Product>> findAllProducts() {
        LOGGER.info("call all the products from DB");
        return Optional.of(hashOperations.entries(HASH_KEY));
    }

    @Override
    public Product saveProduct(Product product) {
      hashOperations.put(HASH_KEY,product.getProductId(),product);
        LOGGER.info("Save the product in DB");
      return product;
    }

    @Override
    public Optional<Product> updateProduct(Product product) {
         hashOperations.put(HASH_KEY,product.getProductId(),product);
        LOGGER.info("Update the product in DB");
        return Optional.of(product);
    }

    @Override
    public void deleteProduct(int productId) {
        LOGGER.info("Delete the product From DB");
        hashOperations.delete(HASH_KEY,productId);
    }
}
