package com.mertalptekin.springrestapplication.presentation.controller;


import com.mertalptekin.springrestapplication.sample.dto.UserDto;
import com.mertalptekin.springrestapplication.sample.model.User;
import com.mertalptekin.springrestapplication.sample.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


// @RestController -> Spring Context Controllerı register etmek için kullanılır

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    private final ModelMapper modelMapper;

    public UsersController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // dtolar dış dünya nesneleri -> entityden alınan bilgiler dtoya maplenir ve dışarı çıkarılır.
    // localhost:5001/api/v1/users
    @GetMapping
    public ResponseEntity<List<UserDto>> index() {

        List<User> users = new ArrayList<>();
        users.add(new User(1, "Mert Alptekin"));
        users.add(new User(2, "Ali Tan"));

        // Java 8 Stream API
        List<UserDto> userResponse = users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();


        return ResponseEntity.ok(userResponse);
    }



}
