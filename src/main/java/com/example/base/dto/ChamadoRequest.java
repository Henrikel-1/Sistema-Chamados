package com.example.base.dto;

import com.example.base.model.Categoria;
import com.example.base.model.Prioridade;
import com.example.base.model.StatusChamado;
import com.example.base.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public record ChamadoRequest(@NotBlank(message = "Titulo obrigatório") @Size(min = 5, max = 100) String titulo
                            ,@NotBlank(message = "Descricão obrigatória") @Size(min = 10, max = 1000) String descricao
                            ,@NotNull Prioridade prioridade,
                             @NotNull Categoria categoria) {

}
