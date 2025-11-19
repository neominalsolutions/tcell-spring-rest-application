package com.mertalptekin.springrestapplication.application.categories.dto;

import lombok.Data;

import java.util.List;

@Data
public class SavedCategoryDto {

    private Long id;
    private String name;
    private List<SavedProductDto> products;

}
