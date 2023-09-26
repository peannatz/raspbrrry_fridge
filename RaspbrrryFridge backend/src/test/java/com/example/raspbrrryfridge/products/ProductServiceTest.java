package com.example.raspbrrryfridge.products;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    ProductService productService;
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    void addProduct() {
        Product productToAdd = new Product();
        productToAdd.setName("Test Product");
        productToAdd.setWeight(100);

        productService.addProduct(productToAdd);

        assertEquals("Test Product", productToAdd.getName());
        assertEquals(100, productToAdd.getWeight());
    }

    @Test
    void deleteProduct() {
        int productIdToDelete = 1;
        Product productToDelete = new Product();
        productToDelete.setId(productIdToDelete);
        productToDelete.setName("Test Product");
        productToDelete.setWeight(100);

        productService.addProduct(productToDelete);
        productService.deleteProduct(productIdToDelete);

        Optional<Product> deletedProduct = productRepository.findById(productIdToDelete);
        assertFalse(deletedProduct.isPresent(), "Product should have been deleted");
    }

    @Test
    void findProductById() {
        int productIdToFind = 1;
        Product productToFind = new Product();
        productToFind.setId(productIdToFind);
        productToFind.setName("Test Product");
        productToFind.setWeight(100);

        productService.addProduct(productToFind);

        Optional<Product> findProduct = productService.findProductById(productIdToFind);
        assertEquals(100, productToFind.getWeight());
        assertEquals("Test Product", productToFind.getName());
    }

    @Test
    void editProduct() {

    }
}
