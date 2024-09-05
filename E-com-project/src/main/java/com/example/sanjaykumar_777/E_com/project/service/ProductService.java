package com.example.sanjaykumar_777.E_com.project.service;

import com.example.sanjaykumar_777.E_com.project.model.Product;
import com.example.sanjaykumar_777.E_com.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    public List<Product> getAllProducts() {
       return productRepository.findAll();
    }

    public Product getProductById(int prodId) {
        return productRepository.findById(prodId).orElse(null);
    }
}
