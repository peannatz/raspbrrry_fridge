package com.example.raspbrrryfridge.domain.products;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public final class ProductDto {
    @NotBlank (message = "name is invalid")
    @Pattern(regexp = "^[a-zA-Z]*$")
    private String name;
    @Min(1)
    private int weight;
    @NotBlank (message = "mhd should not be null")
    private String mhd;
    @NotBlank (message = "url should not be null")
    private String url;
    @NotNull (message = "ean should not be null")
    private Long ean;
    private String tag;

    @NotBlank (message = "category tag should not be blank")
    private String categories_tag;

    public ProductDto(String name, int weight, String mhd, String url, @NotNull(message = "ean should not be null") Long ean, String tag, String categories_tag) {
        this.name = name;
        this.weight = weight;
        this.mhd = mhd;
        this.url = url;
        this.ean = ean;
        this.tag = tag;
        this.categories_tag = categories_tag;
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

    public String getMhd() {
        return mhd;
    }

    public void setMhd(String mhd) {
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCategories_tag() {
        return categories_tag;
    }

    public void setCategories_tag(String categories_tag) {
        this.categories_tag = categories_tag;
    }
}
