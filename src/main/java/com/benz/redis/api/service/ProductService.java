package com.benz.redis.api.service;

import com.benz.redis.api.dao.ProductDAO;
import com.benz.redis.api.exception.DataNotFoundException;
import com.benz.redis.api.model.Product;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class ProductService {

    private ProductDAO productDAO;
    private RedisTemplate<String, Product> redisTemplate;
    private ChannelTopic channelTopic;
    final private static String HASH_KEY="Product";

    public ProductService(ProductDAO productDAO,RedisTemplate<String,Product> redisTemplate,ChannelTopic channelTopic)
    {
       this.productDAO=productDAO;
       this.redisTemplate=redisTemplate;
       this.channelTopic=channelTopic;
    }

    @CachePut(key = "#product.productId",value = "Product")
    public Product saveProduct(Product product)
    {
        return productDAO.saveProduct(product);
    }

    //If the price is less than 25000 then no need to store in the DB
    @Cacheable(key = "#productId",value = "Product",unless = "#result.price<25000")
    public Product getProduct(int productId)
    {
        return productDAO.findProduct(productId).orElseThrow(()->new DataNotFoundException(String.format("Product Not Found With %d",productId)));
    }

    @CachePut(value = "Product")
    public Map<Integer,Product> getProducts()
    {
       return productDAO.findAllProducts().orElseThrow(()->{
           return new DataNotFoundException("No Product Available in DB");
        });
    }

    @CachePut(key="#product.productId",value = "Product")
    public Product updateProduct(Product product)
    {
       return productDAO.updateProduct(product).orElseThrow(()->new DataNotFoundException(String.format("Product is not found with %d",product.getProductId())));
    }

    @CacheEvict(key = "#productId",value = "Product")
    public void deleteProduct(int productId) throws Exception {
        Product product=productDAO.findProduct(productId).orElseThrow(()->new DataNotFoundException(String.format("Product is not found with %d")));
        if(product!=null)
         productDAO.deleteProduct(productId);
        else
            throw new Exception(String.format("Product is not found %d",productId));
    }
}
