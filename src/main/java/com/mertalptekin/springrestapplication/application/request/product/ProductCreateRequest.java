package com.mertalptekin.springrestapplication.application.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

// Entity ile aynı field isimlerini kullanalım ama dğerleri dışarı export ederken daha anlamlı isimler kullanabiliriz.
// ModelMapper ile mapping açısından kolaylık sağlar
// Record tipleri immutable (değiştirilemez) veri taşıma nesneleri oluşturmak için kullanılır.
public record ProductCreateRequest
        (
                @NotNull(message = "Product name cannot be null or empty")       //"" empty olamaz)
        // @NotBlank(message = "Product name cannot be blank")     //"Boş    olamaz "
        @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Product name must be alphanumeric")
        @JsonProperty("product_name") String name,
        @Min(value = 1, message = "Product price must be greater than zero")
        @JsonProperty("product_price") BigDecimal price,


        @Max(value = 100, message = "Product stock must be less than or equal to 100")
        @JsonProperty("product_stock") Integer stock
        ) { }
