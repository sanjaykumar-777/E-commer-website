package com.example.sanjaykumar_777.E_com.project.controller;

import com.example.sanjaykumar_777.E_com.project.model.Product;
import com.example.sanjaykumar_777.E_com.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping("/")
    public String greet(){
        return "Hello";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> allProducts = productService.getAllProducts();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping("/product/{prodId}")
    public ResponseEntity<Product> getProductById(@PathVariable int prodId){
        Product product = productService.getProductById(prodId);
        if(product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        try {
            ResponseEntity<?> product1 = productService.addProduct(product, imageFile);
            return new ResponseEntity<>(product1,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{prodId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int prodId){
        Product product = productService.getProductById(prodId);

        byte[] imageData = product.getImageData();
        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageData);
    }

    @PutMapping("/product/{prodId}")
    public ResponseEntity<String> updateProductById(@PathVariable int prodId,@RequestPart Product product, @RequestPart MultipartFile imageFile) throws IOException {
        Product product1 = null;
        byte[] imageData = product.getImageData();

        try {
            product1 = productService.updateProduct(prodId, product, imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
        }
        if(product1!= null){
            return new ResponseEntity<>("Updates",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/product/{prodId}")
    public ResponseEntity<String> deleteProductById(@PathVariable int prodId){
        Product product = productService.getProductById(prodId);
        if(product != null){
            productService.deleteProduct(prodId);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Product Not Found",HttpStatus.NOT_FOUND);
        }

    }
}
