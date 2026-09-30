package com.example.base.controller;


import com.example.base.dto.AdminRequest;
import com.example.base.dto.AdminResponse;
import com.example.base.service.AdminServiceImp;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminServiceImp adminService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminResponse CadastrarAdmin(@RequestBody @Valid AdminRequest adminRequest) {
        return adminService.cadastrarAdministrador(adminRequest);
    }
    @GetMapping
    public List<AdminResponse> listarAdmins(
    ) {
        return adminService.listarAdministradores();
    }
}
