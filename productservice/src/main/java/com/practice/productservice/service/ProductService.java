package com.practice.productservice.service;

import com.practice.productservice.kafka.ProductProducer;
import com.practice.productservice.model.Product;
import com.practice.productservice.model.ProductEventCreated;
import com.practice.productservice.model.ProductSKU;
import com.practice.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    public ProductRepository repository;

    @Autowired
    private ProductProducer productProducer;

    public Product createProduct(Product product) {
        Product savedProduct = repository.save(product);
        ProductEventCreated event = new ProductEventCreated(savedProduct.getId(),
                savedProduct.getProductSKU().getSkuCode(), savedProduct.getProductSKU().getQuantity());
        productProducer.sendMsgToTopic(event);
        return savedProduct;
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(String id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteProduct(@RequestParam String id) {
        repository.deleteById(id);
    }

    public Map<String, Integer> getCountOfAllProducts() {
        return repository.findAll()
                .stream()
                .filter(product -> product.getProductSKU() != null &&
                        product.getProductSKU().getSkuCode() != null)
                .collect(Collectors.toMap(
                        product -> product.getProductSKU().getSkuCode(),
                        product -> product.getProductSKU().getQuantity(),
                        Integer::sum
                ));
    }

}
