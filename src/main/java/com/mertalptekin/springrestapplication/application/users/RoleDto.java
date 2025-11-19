package com.mertalptekin.springrestapplication.application.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoleDto {

    @JsonProperty("role_id")
     private Integer id;

    @JsonProperty("role_name")
     private String name;

}
