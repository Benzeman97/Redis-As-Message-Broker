package com.benz.redis.api.controller;

import com.benz.redis.api.model.Product;
import com.benz.redis.api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService)
    {
        this.productService=productService;
    }

    @PostMapping(value = "/save",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> saveProduct(@RequestBody Product product)
    {
        if(product.getProductId()!=0 && !product.getProductName().trim().isEmpty() &&
        product.getPrice()!=0 && product.getQty()!=0)
       return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<Integer,Product>> getProducts()
    {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping(value = "/{prodId}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> getProduct(@PathVariable("prodId") int productId)
    {
        if(productId!=0)
        return new ResponseEntity<>(productService.getProduct(productId),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/update",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> updateProduct(@RequestBody Product product)
    {
        if(product.getProductId()!=0 && !product.getProductName().trim().isEmpty() &&
                product.getPrice()!=0 && product.getQty()!=0)
        return ResponseEntity.ok(productService.updateProduct(product));
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{prodId}")
    public void deleteProduct(@PathVariable("prodId") int productId) throws Exception {
        if(productId!=0)
        productService.deleteProduct(productId);
        else
            throw new IllegalArgumentException("Product should not be empty");
    }

    @GetMapping(value = "/send/{productId}")
    public void sendProduct(@PathVariable int productId)
    {
        if(productId!=0)
            productService.sendProduct(productId);
        else
            throw new IllegalArgumentException("ProductId should not be bull");
    }
}
