package com.example.raspbrrryfridge.products;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    Optional<Product> findByMhd(LocalDate mhd);
}
