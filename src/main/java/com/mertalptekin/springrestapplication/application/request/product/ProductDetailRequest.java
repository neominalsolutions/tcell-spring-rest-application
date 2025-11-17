package com.mertalptekin.springrestapplication.application.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductDetailRequest(@JsonProperty("product_id") Integer id) {
}
