package com.mertalptekin.springrestapplication.sample.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// request nesnesi
public class UserDto {
    @JsonProperty("user_id")
    private Integer id;

    public UserDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserDto() {

    }

    @JsonProperty("username")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
