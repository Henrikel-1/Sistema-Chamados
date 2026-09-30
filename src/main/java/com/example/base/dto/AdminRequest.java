package com.example.base.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminRequest(
                           @NotBlank(message = "nome obrigatório") @Size(min = 3, max = 100) String nome,
                           @NotBlank(message = "Email obrigatório") @Email String email,
                           @NotBlank(message = "Senha obrigatória") @Size(min = 8, max = 100) String senha) {
}
