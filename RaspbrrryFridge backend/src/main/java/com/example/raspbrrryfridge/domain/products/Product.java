package com.example.raspbrrryfridge.domain.products;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank
    private String name;
    @NotBlank
    private int weight;
    private LocalDate mhd;
    @NotBlank
    private String url;
    @NotBlank
    private Long ean;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public LocalDate getMhd() {
        return mhd;
    }

    public void setMhd(LocalDate mhd) {
        this.mhd = mhd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getEan() {
        return ean;
    }

    public void setEan(Long ean) {
        this.ean = ean;
    }
}
