package com.mertalptekin.springrestapplication.presentation.controller;

import com.mertalptekin.springrestapplication.application.users.RoleDto;
import com.mertalptekin.springrestapplication.application.users.UserDto;
import com.mertalptekin.springrestapplication.domain.entity.Role;
import com.mertalptekin.springrestapplication.domain.entity.User;
import com.mertalptekin.springrestapplication.infra.repository.IRoleRepository;
import com.mertalptekin.springrestapplication.infra.repository.IUserRepository;
import com.mertalptekin.springrestapplication.sample.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final IRoleRepository roleRepository;

    public UserController(IUserRepository userRepository, ModelMapper modelMapper, IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }


    @GetMapping
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    // POSTMAN üzerinden user ile rolleri birlikte ekleyeceğiz.

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);

        List<String> roleNames = userDto.getRoles().stream().map(RoleDto::getName).toList();

        // findByNameIgnoreCaseIn -> çalışıyor

       List<Role> roles =  roleRepository.findByNameInIgnoreCase(roleNames);

       // aynı idli olan rolleri bulursan bunları user'a ata
       if(!roles.isEmpty()) {
           user.setRoles(roles);
       }

        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

}
