package com.example.base.service;

import com.example.base.dto.AdminRequest;
import com.example.base.dto.AdminResponse;

import java.util.List;

public interface AdminService {

    AdminResponse cadastrarAdministrador(AdminRequest adminRequest);
    AdminResponse signup(AdminRequest adminRequest);
    List<AdminResponse> listarAdministradores();
}
