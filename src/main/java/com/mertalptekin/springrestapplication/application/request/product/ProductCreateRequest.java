package com.mertalptekin.springrestapplication.application.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

// Entity ile aynı field isimlerini kullanalım ama dğerleri dışarı export ederken daha anlamlı isimler kullanabiliriz.
// ModelMapper ile mapping açısından kolaylık sağlar
// Record tipleri immutable (değiştirilemez) veri taşıma nesneleri oluşturmak için kullanılır.
public record ProductCreateRequest
        (
        @JsonProperty("product_name") String name,
        @JsonProperty("product_price") BigDecimal price,
        @JsonProperty("product_stock") Integer stock
        ) { }
