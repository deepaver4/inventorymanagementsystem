package com.practice.productservice.controller;

import com.practice.productservice.model.Product;
import com.practice.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @PostMapping("/create")
    public Product createProduct(@RequestBody Product product){
        return service.createProduct(product);
    }

    @GetMapping("/getAllProducts")
    public List<Product> getProducts(){
        return service.getAllProducts();
    }

    @GetMapping("/getProduct")
    public Product getProductById(@RequestParam String id){
        return service.getProductById(id);
    }

    @DeleteMapping("/deleteProduct")
    public void deleteProduct(@RequestParam String id){
        service.deleteProduct(id);
    }

    @GetMapping("/getCountOfAllProduct")
    public Map<String, Integer> getCountOfAllProducts(){
        return service.getCountOfAllProducts();
    }
}
