package com.example.base.service;

import com.example.base.core.exception.EmailJaCadastradoException;
import com.example.base.dao.AdminRepository;
import com.example.base.dto.AdminRequest;
import com.example.base.dto.AdminResponse;
import com.example.base.model.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AdminServiceImp implements AdminService {

    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    public AdminResponse cadastrarAdministrador(AdminRequest adminRequest) {
        if (adminRepository.existsByEmail(adminRequest.email())) {
            throw new EmailJaCadastradoException(
                    "Já existe um administrador cadastrado com o e-mail: " + adminRequest.email()
            );
        }
       Usuario usuario = new Usuario();
       usuario.setPapel("ADMIN");
       usuario.setNome(adminRequest.nome());
       usuario.setEmail(adminRequest.email());
       usuario.setSenha(passwordEncoder.encode(adminRequest.senha()));
       adminRepository.save(usuario);

       return new AdminResponse(usuario.getId(), ("ADMIN"), adminRequest.nome(), adminRequest.email());
    }

    public List<AdminResponse> listarAdministradores() {
        return adminRepository.findAll().stream().map(admin -> new AdminResponse(admin.getId(), admin.getPapel(), admin.getNome(), admin.getEmail())).toList();
    }
}
