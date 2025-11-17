package com.mertalptekin.springrestapplication.application.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;

// Hangi ürünün hangi alanlarını güncelleyeceğiz ?
public record ProductUpdateRequest(@JsonProperty("product_id") Integer id,
                                   @JsonProperty("product_name") String name,
                                   @JsonProperty("product_price") Double price,
                                   @JsonProperty("product_quantity") Integer quantity) {
}
