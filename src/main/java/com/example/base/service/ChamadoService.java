package com.example.base.service;

import com.example.base.dto.ChamadoRequest;
import com.example.base.dto.ChamadoResponse;
import com.example.base.model.Categoria;
import com.example.base.model.StatusChamado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChamadoService {
    ChamadoResponse cadastrarNovoChamado(Long usuarioId, ChamadoRequest chamadoRequest);
    Page<ChamadoResponse> listarMeusChamados(Long usuarioId, StatusChamado status, String busca, Pageable pageable);
    ChamadoResponse buscarPorId(Long chamadoId, Long usuarioId, boolean admin);
    Page<ChamadoResponse> listarTodos(StatusChamado status, Categoria categoria, Pageable pageable);
}