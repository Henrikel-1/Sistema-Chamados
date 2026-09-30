package com.example.base.controller;

import com.example.base.dto.AdminRequest;
import com.example.base.dto.AdminResponse;
import com.example.base.dto.LoginReqDto;
import com.example.base.model.Usuario;
import com.example.base.service.AdminService;
import com.example.base.service.AuthService;
import jakarta.persistence.Access;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {


    private final AuthService authService;
    private final AdminService adminService;

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginReqDto dto) {
        return authService.login(dto);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminResponse signUp(@RequestBody @Valid AdminRequest dto) {
        return adminService.signup(dto);
    }
}
