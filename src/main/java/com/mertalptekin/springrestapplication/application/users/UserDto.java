package com.mertalptekin.springrestapplication.application.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    @JsonProperty("user_id")
    private String id;

    @JsonProperty("username")
    @Size(min = 5, max = 10, message = "Username must be between 5 and 10 characters")
    private String username;

    @JsonProperty("password")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,12}$",
            message = "Password must be 8-12 characters long, include upper and lower case letters, a digit, and a special character"
    )
    private String password;

    @JsonProperty("user-roles")
    private List<RoleDto> roles;


}
