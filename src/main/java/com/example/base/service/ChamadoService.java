package com.example.base.service;

import com.example.base.dto.ChamadoRequest;
import com.example.base.dto.ChamadoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChamadoService {
    ChamadoResponse cadastrarNovoChamado(Long usuarioId, ChamadoRequest chamadoRequest);
    Page<ChamadoResponse> ListarChamadosAbertosUsuario (Long usuarioId, Pageable pageable);
}
