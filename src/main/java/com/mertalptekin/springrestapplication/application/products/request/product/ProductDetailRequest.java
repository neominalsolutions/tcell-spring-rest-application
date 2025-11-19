package com.mertalptekin.springrestapplication.application.products.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductDetailRequest(@JsonProperty("product_id") Integer id) {
}
