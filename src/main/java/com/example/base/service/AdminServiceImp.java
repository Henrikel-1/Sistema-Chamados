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

    private static final String ADMIN = "ADMIN";
    private static final String USUARIO = "USUARIO";

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminResponse cadastrarAdministrador(AdminRequest adminRequest) {
        return cadastrar(adminRequest, ADMIN);
    }

    @Override
    public AdminResponse signup(AdminRequest adminRequest) {
        boolean primeiroAdmin = !adminRepository.existsByPapel(ADMIN);
        return cadastrar(adminRequest, primeiroAdmin ? ADMIN : USUARIO);
    }

    @Override
    public List<AdminResponse> listarAdministradores() {
        return adminRepository.findAll().stream()
                .map(u -> new AdminResponse(u.getId(), u.getPapel(), u.getNome(), u.getEmail()))
                .toList();
    }

    private AdminResponse cadastrar(AdminRequest request, String papel) {
        if (adminRepository.existsByEmail(request.email())) {
            throw new EmailJaCadastradoException(
                    "Já existe um usuário cadastrado com o e-mail: " + request.email());
        }
        Usuario usuario = new Usuario();
        usuario.setPapel(papel);
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(passwordEncoder.encode(request.senha()));
        adminRepository.save(usuario);

        return new AdminResponse(usuario.getId(), papel, usuario.getNome(), usuario.getEmail());
    }
}
