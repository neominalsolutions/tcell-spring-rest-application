package com.mertalptekin.springrestapplication.presentation.filter;

import com.mertalptekin.springrestapplication.domain.service.CustomUserDetailService;
import com.mertalptekin.springrestapplication.infra.jwt.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Token Lifetime expired ise içeri almaz. 401
// Token signature ile JWT uyumsuz ise içeri almaz. 401
// Authorization Header Set edilmediyse içeri almaz 401


@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailService customUserDetailService;

    public JwtRequestFilter(final JwtService jwtService, CustomUserDetailService customUserDetailService) {
        this.jwtService = jwtService;
        this.customUserDetailService = customUserDetailService;
    }

    // Token  Valid ise kullancıyı anlık olarak authenticated işaretle
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JWT request filter Per Request");
        try{
            // Bearer TokenValue
            String authorizationHeader = request.getHeader("Authorization");

            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                log.info("protected request with header");

                String accessToken = authorizationHeader.substring(7);
                Claims claims = jwtService.parseToken(accessToken);
                String username =  claims.getSubject();

                // token içerisinde subject değeri sistemde var mı ?
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

                // SecurityContextHolder.getContext().getAuthentication() == null -> Her yeni istek de null gelir, çünkü session tabanlı çalışmıyoruz stateless çalışıyoruz.

                // Sistemde kullanıcı authenticated olarak işaretli değilse ve username sistemde varsa
                if(SecurityContextHolder.getContext().getAuthentication() == null && username != null) {
                    // Kullanıcıya authenticated olarak iaşaretle
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    // Kullanıcıyı token üzerinden web uygulamasında oturum açmış olarak işaretle
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    // süreci devam ettir.
                    filterChain.doFilter(request, response);

                }
            } else {
                log.info("public request with no header");
                // süreci devam ettir..
                filterChain.doFilter(request, response);
            }


        } catch (Exception e) {
            log.error("Token Parse exception" + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
