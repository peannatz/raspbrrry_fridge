package com.example.raspbrrryfridge.products;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(ProductDto productDto){
        Product product = new Product();
        product.setName(productDto.name());
        product.setWeight(productDto.weight());
        productRepository.save(product);
    }

    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }

    public void editProduct(int id, Product product){
        Product oldProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        oldProduct.setName(product.getName());
        oldProduct.setWeight(product.getWeight());
        productRepository.save(oldProduct);
    }

    public Optional<Product> findProductById(int id){
        return productRepository.findById(id);
    }

    public List<Product> findAllProducts() {
        return (List<Product>) productRepository.findAll();
    }
}
