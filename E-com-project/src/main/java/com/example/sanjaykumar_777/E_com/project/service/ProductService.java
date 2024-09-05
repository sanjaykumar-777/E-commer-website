package com.example.sanjaykumar_777.E_com.project.service;

import com.example.sanjaykumar_777.E_com.project.model.Product;
import com.example.sanjaykumar_777.E_com.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public ResponseEntity<?> addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        Product product1 = productRepository.save(product);
        return new ResponseEntity<>(product1, HttpStatus.CREATED);
    }
}
