package com.mertalptekin.springrestapplication.application.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;

// Hangi üründe ne kadarlık bir stok düşücez ?
public record ProductStockOutRequest(
        @JsonProperty("product_id") Integer id,
        @JsonProperty("quantity") Integer quantity) {
}
