package com.mertalptekin.springrestapplication.application.products.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    @JsonProperty("product_id")
    private Integer id; // entity ile maplemek için aynı olsun dışarı çıkarken product_id olsun;

    @JsonProperty("product_name")
    private String name;

    @JsonProperty("unit_price")
    private BigDecimal price;

    @JsonProperty("stock_quantity")
    private Integer stock;

}
