package com.example.base.controller;

import com.example.base.dto.ChamadoRequest;
import com.example.base.dto.ChamadoResponse;
import com.example.base.dto.UsuarioLogadoDto;
import com.example.base.model.Chamado;
import com.example.base.model.Usuario;
import com.example.base.service.ChamadoServiceImp;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoServiceImp chamadoServiceImp;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChamadoResponse cadastrar(@AuthenticationPrincipal UsuarioLogadoDto usuarioId, @RequestBody @Valid ChamadoRequest chamadoRequest){
        return chamadoServiceImp.cadastrarNovoChamado(usuarioId.id(), chamadoRequest);
    }

    @GetMapping("/me")
    public Page<ChamadoResponse> ListarChamadosAbertosUsuario (@AuthenticationPrincipal UsuarioLogadoDto usuarioId, @PageableDefault(page = 0, size = 10, sort = "titulo", direction = Sort.Direction.ASC) Pageable pageable){
        return chamadoServiceImp.ListarChamadosAbertosUsuario(usuarioId.id(), pageable);
    }
}
