package com.example.raspbrrryfridge.domain.products;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
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
    public ResponseEntity<Product> addProduct(@RequestBody @Valid ProductDto productDto){
        return productService.addProduct(productDto);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Successfully deleted the product");
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<String> editProduct(@PathVariable int id, @RequestBody ProductDto productDto){
        productService.editProduct(id, productDto);
        return ResponseEntity.ok("Successfully edited the Product");
    }

    @GetMapping("/find/{id}")
    public Optional<Product> findProductById(@PathVariable int id){
        return productService.findProductById(id);
    }

    @GetMapping("/findAll")
    public List<Product> findAll(){
        return productService.findAllProducts();
    }

    @GetMapping("/findByMhd/{mhd}")
    public Optional<Product> findProductByMhd(@PathVariable String mhd){
        return productService.findProductByMhd(LocalDate.parse(mhd));
    }

    @GetMapping("/findByEan/{ean}")
    public List<Product> findProductByEan(@PathVariable Long ean){
        return productService.findProductByEan(ean);
    }

    @GetMapping("/findByTag/{tag}")
    public List<Product> findProductByTag(@PathVariable String tag){
        return productService.findProductByTag(tag);
    }
}
