package com.mertalptekin.springrestapplication.application.products.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

// Hangi ürünün hangi alanlarını güncelleyeceğiz ?
public record ProductUpdateRequest(@JsonProperty("product_id") Integer id,
                                   @JsonProperty("product_name") String name,
                                   @JsonProperty("product_price") BigDecimal price,
                                   @JsonProperty("product_stock") Integer stock) {
}

