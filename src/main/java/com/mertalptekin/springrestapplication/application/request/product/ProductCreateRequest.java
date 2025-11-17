package com.mertalptekin.springrestapplication.application.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;

// Record tipleri immutable (değiştirilemez) veri taşıma nesneleri oluşturmak için kullanılır.
public record ProductCreateRequest
        (
        @JsonProperty("product_name") String productName,
        @JsonProperty("product_price") Double productPrice,
        @JsonProperty("product_stock") Integer productStock
        ) { }
