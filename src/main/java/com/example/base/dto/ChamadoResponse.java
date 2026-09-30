package com.example.base.dto;

import com.example.base.model.Categoria;
import com.example.base.model.Prioridade;
import com.example.base.model.StatusChamado;
import com.example.base.model.Usuario;
import jakarta.persistence.JoinColumn;

public record ChamadoResponse(long id,
                              String titulo,
                              String descricao,
                              StatusChamado status,
                              Prioridade prioridade,
                              Categoria categoria,
                              AdminResponse  adminResponse

) {
}
