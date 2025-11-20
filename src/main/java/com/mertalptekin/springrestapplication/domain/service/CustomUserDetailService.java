package com.mertalptekin.springrestapplication.domain.service;

import com.mertalptekin.springrestapplication.infra.repository.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// 1- UserNamePasswordAuthentication ile AuthenticationManager.auth -> CustomUserDetailService.loadUserByUsername
// 2- loadUserByUsername -> username göre kullanıcı bulma.
// 3- DaoAuthenticationProvider -> üzerinden retrieveUser methodunu çalıştırır
// 4-   AuthenticationManager.auth -> methodundan devam eder. this.preAuthenticationChecks.check(user);
// 5- DaoAuthenticationProvider additionalAuthenticationChecks üzerinden kullanıcı ve password hash bilgilerini kontrol eder.

// kullanıcının login işlemi sırasında user detaylarını yüklemek için kullanılır.

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final IUserRepository userRepository;

    public CustomUserDetailService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}

