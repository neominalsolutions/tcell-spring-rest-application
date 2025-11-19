package com.mertalptekin.springrestapplication.infra.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtService {

    // Not: Key Vault üzerind etutulmalı. Azure Key Vault, AWS Secrets Manager gibi.
    private final String secretKey = "1c8201d66f16acfc20c008a4f3b4904b928764e6f48a3734a999181346e4a93x";

    // Token oluşturma
    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        List<GrantedAuthority> authorities = userDetails.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.toString())).collect(Collectors.toList());
        claims.put("roles", authorities);

        // tokenda role değerleri eklenmiş oldu.

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .addClaims(claims) // additional claims setledik, set dersek diğer claimleri ezer.addtional claims ekleme işlemi için addClaims kullanılır.
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 dakika geçerli
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS512) // yeni versiyon
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .compact();
    }

    // Token parse etme ve içerisindeki claimleri alma
    public Claims parseToken(String token){ // token parse edilmezse token değişmiş ise burası çalışmaz.
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        Claims claims = parseToken(token);
        return new Date().before(claims.getExpiration());
    }

}
