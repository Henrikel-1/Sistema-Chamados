package com.example.base.controller;

import com.example.base.dto.ChamadoRequest;
import com.example.base.dto.ChamadoResponse;
import com.example.base.dto.UsuarioLogadoDto;
import com.example.base.model.Categoria;
import com.example.base.model.StatusChamado;
import com.example.base.service.ChamadoService;
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

    private final ChamadoServiceImp chamadoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChamadoResponse cadastrar(@AuthenticationPrincipal UsuarioLogadoDto usuario,
                                     @RequestBody @Valid ChamadoRequest request) {
        return chamadoService.cadastrarNovoChamado(usuario.id(), request);
    }

    @GetMapping("/me")
    public Page<ChamadoResponse> listarMeus(
            @AuthenticationPrincipal UsuarioLogadoDto usuario,
            @RequestParam(required = false) StatusChamado status,
            @RequestParam(required = false) String busca,
            @PageableDefault(size = 10, sort = "titulo", direction = Sort.Direction.ASC) Pageable pageable) {
        return chamadoService.listarMeusChamados(usuario.id(), status, busca, pageable);
    }

    @GetMapping("/admin")
    public Page<ChamadoResponse> listarTodos(
            @RequestParam(required = false) StatusChamado status,
            @RequestParam(required = false) Categoria categoria,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return chamadoService.listarTodos(status, categoria, pageable);
    }

    @GetMapping("/{id}")
    public ChamadoResponse detalhar(@AuthenticationPrincipal UsuarioLogadoDto usuario,
                                    @PathVariable Long id) {
        return chamadoService.buscarPorId(id, usuario.id(), usuario.admin());
    }
}