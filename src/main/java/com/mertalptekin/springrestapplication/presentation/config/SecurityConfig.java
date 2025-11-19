package com.mertalptekin.springrestapplication.presentation.config;


import com.mertalptekin.springrestapplication.presentation.filter.JwtRequestFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    private static void customize(ExceptionHandlingConfigurer<HttpSecurity> e) {
        e.authenticationEntryPoint((request, response, authException) -> {
            System.out.println("Unauthorized request - " + authException.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"); // filterdan geçemezsek 401 hatası döner
        });
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // form gönderilerinde Request Forgery korumasını devre dışı bırakır.
                .authorizeHttpRequests(requests ->

                        requests.requestMatchers("/api/v1/auth/**").permitAll() // kimlik doğrulama ile ilgili endpointlere herkes erişebilir.
                         .anyRequest().authenticated()
                );
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Restful servislerde oturum yönetimini devre dışı bırakır.
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // OnceRequestFilter'ı güvenlik filtre zincirine ekler.
        http.exceptionHandling(SecurityConfig::customize); // dofilter chaninde geçemezsek buraya gireriz.

        return http.build();
    }


}
