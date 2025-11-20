package com.mertalptekin.springrestapplication.presentation.controller;

import com.mertalptekin.springrestapplication.application.users.RoleDto;
import com.mertalptekin.springrestapplication.application.users.UserDto;
import com.mertalptekin.springrestapplication.domain.entity.AppRole;
import com.mertalptekin.springrestapplication.domain.entity.AppUser;
import com.mertalptekin.springrestapplication.infra.repository.IRoleRepository;
import com.mertalptekin.springrestapplication.infra.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final IRoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(IUserRepository userRepository, ModelMapper modelMapper, IRoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    // POSTMAN üzerinden user ile rolleri birlikte ekleyeceğiz.

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {

        // Kullacının parola bilgisini şifreli olarak set etmemiz gerekecek.
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword())); // hashed password

        AppUser user = modelMapper.map(userDto, AppUser.class);
        List<String> roleNames = userDto.getRoles().stream().map(RoleDto::getName).toList();

        // findByNameIgnoreCaseIn -> çalışıyor

       List<AppRole> roles =  roleRepository.findByNameInIgnoreCase(roleNames); // Query Method

       // aynı idli olan rolleri bulursan bunları user'a ata
       if(!roles.isEmpty()) {
           user.setRoles(roles);
       }

        userRepository.save(user);
        user.setPassword(null);

        return modelMapper.map(user, UserDto.class);
    }

}
