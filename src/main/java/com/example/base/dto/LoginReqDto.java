package com.example.base.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record LoginReqDto(
        @NotBlank(message = "Email deve ser informado!")
        @Email(message = "Email inválido")
        String email,
        @NotBlank(message = "senha deve ser informado")
        @Length(min = 8, message = "senha deve ter pelo menos 8 caracteres")
        String senha
) {
}
