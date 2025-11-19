package com.mertalptekin.springrestapplication.application.products.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateResponse {

    @JsonProperty("product_id")
    private Integer id;

    @JsonProperty("product_name")
    private String name;
}
