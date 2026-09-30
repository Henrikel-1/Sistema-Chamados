package com.example.base.dao;

import com.example.base.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(@NotBlank(message = "Email obrigatório") @Email String email);
    Optional<Usuario> findByEmail(String email);
    boolean existsByPapel(String papel);
}
