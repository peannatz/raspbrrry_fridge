package com.example.raspbrrryfridge.domain.products;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    ProductRepository productRepository;
    ProductConverterService productConverterService;

    public ProductService(ProductRepository productRepository, ProductConverterService productConverterService) {
        this.productRepository = productRepository;
        this.productConverterService = productConverterService;
    }

    public ResponseEntity<Product> addProduct(ProductDto productDto){
        try{
            Product product = new Product();
            productConverterService.convertToEntity(productDto, product);
            productRepository.save(product);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }

    public void editProduct(int id, ProductDto productDto){
        Product oldProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        oldProduct.setId(id);
        productConverterService.convertToEntity(productDto, oldProduct);
        productRepository.save(oldProduct);
    }

    public Optional<Product> findProductById(int id){
        return productRepository.findById(id);
    }

    public List<Product> findAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public Optional<Product> findProductByMhd(LocalDate mhd){
        return productRepository.findByMhd(mhd);
    }

    public List<Product> findProductByEan(Long ean){
        return productRepository.findByEan(ean);
    }

    public List<Product> findProductByTag(String tag){
        return productRepository.findByTag(tag);
    }
}
