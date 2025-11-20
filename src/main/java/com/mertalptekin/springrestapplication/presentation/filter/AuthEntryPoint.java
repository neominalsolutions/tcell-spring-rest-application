package com.mertalptekin.springrestapplication.presentation.filter;

import com.mertalptekin.springrestapplication.infra.jwt.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


// Token ile 401 yada 403 hatalarında devreye girecek sınıf
// Eğer ki SecurityConfig dosyasında geçemezsek buradaya düşeriz
// Authentication sürecinde bir hata durumunda düşeriz
@Component
@Slf4j
public class AuthEntryPoint implements AuthenticationEntryPoint {

    private final JwtService jwtService;

    public AuthEntryPoint(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        log.warn("Unauthorized request commence - {}", authException.getMessage());


        response.setContentType("application/json");

        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                jwtService.parseToken(token); // parse edersem token süresi geçmiş ama doğru bir token. başkası tarafında daha önce oluşturulmuş bir token.

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write(
                        "{ \"error\": \"Forbidden\", " +
                                "\"message\": \"Bu kaynağa erişim yetkiniz yok\" }");

            } catch (Exception e) {
                // Geçersiz token ise 401 Unauthorized döner. Değişen bir token olabilir.
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(
                        "{ \"error\": \"Unauthorized\", " +
                                "\"message\": \"Geçersiz token\" }"
                );
            }
        } else {
            // yetki dahilinde girmem gereken yere yetkisiz erişim yapmaya çalıştım.
            // Kullanıcı kimliği doğrulanmamış ise 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(
                    "{ \"error\": \"Unauthorized\", " +
                            "\"message\": \"Kimlik doğrulama gerekli\" }"
            );

        }

    }
}
