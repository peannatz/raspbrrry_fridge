package com.example.raspbrrryfridge.domain.products;

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
    ProductValidator productValidator;

    public ProductController(ProductService productService, ProductValidator productValidator) {
        this.productService = productService;
        this.productValidator = productValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductDto productDto){
        if(!productValidator.isValid(productDto)){
            return ResponseEntity.badRequest().body("This Product is missing Information");
        }
        productService.addProduct(productDto);
        return ResponseEntity.ok("Successfully added the Product to your Fridge");
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Successfully deleted the product");
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<String> editProduct(@PathVariable int id, @RequestBody ProductDto productDto){
        if(!productValidator.isValid(productDto)){
            return ResponseEntity.badRequest().body("The Product you sent is missing Information");
        }
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
    public List<Product> findProductbyEan(@PathVariable Long ean){
        return productService.findProductByEan(ean);
    }
}
