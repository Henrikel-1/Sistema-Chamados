package com.example.base.service;

import com.example.base.core.security.JwtService;
import com.example.base.dto.LoginReqDto;
import com.example.base.dto.LoginRespDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImp implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;


    @Override
    public String login(LoginReqDto dto) {
        Authentication authenticaton =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                dto.email(), dto.senha()));

        return jwtService.generateToken(authenticaton);
    }

}
