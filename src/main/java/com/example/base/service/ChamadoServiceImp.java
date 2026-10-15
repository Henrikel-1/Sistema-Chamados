package com.example.base.service;

import com.example.base.core.exception.AcessoNegadoException;
import com.example.base.core.exception.ChamadoNotFoundException;
import com.example.base.core.exception.UsuarioNotFoundException;
import com.example.base.dao.AdminRepository;
import com.example.base.dao.ChamadoRepository;
import com.example.base.dto.AdminResponse;
import com.example.base.dto.ChamadoRequest;
import com.example.base.dto.ChamadoResponse;
import com.example.base.model.Categoria;
import com.example.base.model.Chamado;
import com.example.base.model.StatusChamado;
import com.example.base.model.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ChamadoServiceImp implements ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final AdminRepository adminRepository;

    @Override
    public ChamadoResponse cadastrarNovoChamado(Long usuarioId, ChamadoRequest chamadoRequest) {
        Usuario usuario = adminRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario não encontrado"));

        Chamado chamado = new Chamado();
        chamado.setDescricao(chamadoRequest.descricao());
        chamado.setTitulo(chamadoRequest.titulo());
        chamado.setUsuario(usuario);
        chamado.setStatus(StatusChamado.ABERTO);
        chamado.setPrioridade(chamadoRequest.prioridade());
        chamado.setCategoria(chamadoRequest.categoria());
        chamadoRepository.save(chamado);
        return toResponse(chamado);
    }

    @Override
    public Page<ChamadoResponse> listarMeusChamados(Long usuarioId, StatusChamado status,
                                                    String busca, Pageable pageable) {
        Page<Chamado> chamados;

        if (busca != null && !busca.isBlank()) {

            if (status == null) {
                chamados = chamadoRepository.buscarDoUsuario(usuarioId, busca.trim(), pageable);
            } else {
                chamados = chamadoRepository.buscarDoUsuarioComStatus(usuarioId, status, busca.trim(), pageable);
            }
        } else if (status == null) {
            chamados = chamadoRepository.findAllByUsuario_Id(usuarioId, pageable);
        } else {
            chamados = chamadoRepository.findAllByUsuario_IdAndStatus(usuarioId, status, pageable);
        }

        return chamados.map(this::toResponse);
    }

    @Override
    public ChamadoResponse buscarPorId(Long chamadoId, Long usuarioId, boolean admin) {
        Chamado chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new ChamadoNotFoundException("Chamado não encontrado"));

        if (!admin && !chamado.getUsuario().getId().equals(usuarioId)) {
            throw new AcessoNegadoException("Você não tem permissão para acessar este chamado");
        }
        return toResponse(chamado);
    }

    @Override
    public Page<ChamadoResponse> listarTodos(StatusChamado status, Categoria categoria, Pageable pageable) {
        Page<Chamado> chamados;

        if (status == null && categoria == null) {
            chamados = chamadoRepository.findAll(pageable);
        } else if (status != null && categoria == null) {
            chamados = chamadoRepository.findAllByStatus(status, pageable);
        } else if (status == null) {
            chamados = chamadoRepository.findAllByCategoria(categoria, pageable);
        } else {
            chamados = chamadoRepository.findAllByStatusAndCategoria(status, categoria, pageable);
        }

        return chamados.map(this::toResponse);
    }

    public ChamadoResponse toResponse(Chamado chamado) {
        Usuario u = chamado.getUsuario();
        AdminResponse adminResponse = new AdminResponse(u.getId(), u.getPapel(), u.getNome(), u.getEmail());
        return new ChamadoResponse(chamado.getId(), chamado.getTitulo(), chamado.getDescricao(),
                chamado.getStatus(), chamado.getPrioridade(), chamado.getCategoria(), adminResponse);
    }
}