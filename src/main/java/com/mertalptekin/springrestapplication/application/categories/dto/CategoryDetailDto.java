package com.mertalptekin.springrestapplication.application.categories.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDetailDto {


    @JsonProperty("category_id")
    private Integer id;

    @JsonProperty("category_name")
    private String name;

    @JsonProperty("products")
    private List<ProductDetailDto> products;

}
