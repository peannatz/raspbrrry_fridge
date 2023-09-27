package com.example.raspbrrryfridge.products;

import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public void addProduct(@RequestBody ProductDto productDto){
        productService.addProduct(productDto);
    }

    @PostMapping("/delete/{id}")
    public void deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);
    }

    @PostMapping("/edit/{id}")
    public void editProduct(@PathVariable int id, @RequestBody Product product){
        productService.editProduct(id, product);
    }

    @GetMapping("/find/{id}")
    public Optional<Product> findProductById(@PathVariable int id){
        return productService.findProductById(id);
    }
}
