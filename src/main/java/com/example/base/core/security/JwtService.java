package com.example.base.core.security;

import org.springframework.security.core.Authentication;

public interface JwtService {
    String generateToken(Authentication authentication);
    Authentication getAuthentication(String token);
}
