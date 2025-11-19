package com.mertalptekin.springrestapplication.application.categories.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductDetailDto {

    @JsonProperty("product_id")
    private Integer id;

    @JsonProperty("product_name")
    private String name;
}
