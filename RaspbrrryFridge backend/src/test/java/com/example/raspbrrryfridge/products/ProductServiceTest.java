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
        ProductDto productToAdd = new ProductDto("Test Product", 100, "2023-09-30T00:00:00");

        productService.addProduct(productToAdd);

        assertEquals("Test Product", productToAdd.name());
        assertEquals(100, productToAdd.weight());
    }

    @Test
    void deleteProduct() {
        int productIdToDelete = 1;
        ProductDto productToDelete = new ProductDto("Test Product", 100, "2023-09-30T00:00:00");

        productService.addProduct(productToDelete);
        productService.deleteProduct(productIdToDelete);

        Optional<Product> deletedProduct = productRepository.findById(productIdToDelete);
        assertFalse(deletedProduct.isPresent(), "Product should have been deleted");
    }

    @Test
    void findProductById() {
        int productIdToFind = 1;
        ProductDto productToFind = new ProductDto("Test Product", 100, "2023-09-30T00:00:00");

        productService.addProduct(productToFind);

        Optional<Product> findProduct = productService.findProductById(productIdToFind);
        assertEquals(100, productToFind.weight());
        assertEquals("Test Product", productToFind.name());
    }


    //TODO
    @Test
    void editProduct() {

    }
}
