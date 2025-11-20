package com.mertalptekin.springrestapplication.presentation.config;


import com.mertalptekin.springrestapplication.presentation.filter.AuthEntryPoint;
import com.mertalptekin.springrestapplication.presentation.filter.JwtRequestFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final AuthEntryPoint authEntryPoint;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter, AuthEntryPoint authEntryPoint) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.authEntryPoint = authEntryPoint;
    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET","POST"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // ROLE_Admin, Rolleri bu şekilde tanımlamalıyız  -> hasAnyRole("Admin")
    // ROLE_Admin şeklinde db tanımlı ise o zaman hasAnyAuthority("ROLE_Admin") şeklinde kullanmalıyız.

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // form gönderilerinde Request Forgery korumasını devre dışı bırakır.
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS burada etkin
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/api/v1/auth/**","/h2-console/**").permitAll()
                                .requestMatchers("/api/v1/users/**").hasAnyAuthority("ROLE_Admin") // user endpoint girecek olanlar sadece admin yetkisine sahip olmalı, route bazlı yetkilendirme
                                .anyRequest()
                                .authenticated()
                );
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Restful servislerde oturum yönetimini devre dışı bırakır.

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // OnceRequestFilter'ı güvenlik filtre zincirine ekler.
        http.exceptionHandling(e-> e.authenticationEntryPoint(authEntryPoint)); // dofilter chaninde geçemezsek buraya gireriz.
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)); // H2 Console için frame desteği

        return http.build();
    }


}
