package com.example.base.core.security;

import com.example.base.dto.UsuarioLogadoDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {

    private final SecretKey secretKey = Keys
            .hmacShaKeyFor("minha-chave-ultra-secrata-de-pelo-menos-256-bits!!!"
                    .getBytes());

    @Override
    public String generateToken(Authentication authentication) {
        return Jwts.builder().subject(authentication.getName()).
                claim("authorities", authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()).
                signWith(secretKey).
                expiration(new Date(System.currentTimeMillis() + 3600 * 1000)).
                compact();
    }

    @Override
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String userId = claims.getSubject();

        List<String> authorities = claims.get("authorities", List.class);

        var grantedAuthorities = authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        var  usuarioLogadoDto = new UsuarioLogadoDto(Long.parseLong(userId));
        return new UsernamePasswordAuthenticationToken(usuarioLogadoDto, token, grantedAuthorities);
    }
}
