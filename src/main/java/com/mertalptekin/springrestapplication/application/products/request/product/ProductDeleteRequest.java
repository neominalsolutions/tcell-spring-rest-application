package com.mertalptekin.springrestapplication.application.products.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public record ProductDeleteRequest(@JsonProperty("product_id") Integer id) {
}
