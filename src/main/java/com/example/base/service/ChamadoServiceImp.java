package com.example.base.service;

import com.example.base.core.exception.UsuarioNotFoundException;
import com.example.base.dao.AdminRepository;
import com.example.base.dao.ChamadoRepository;
import com.example.base.dto.AdminResponse;
import com.example.base.dto.ChamadoRequest;
import com.example.base.dto.ChamadoResponse;
import com.example.base.model.Chamado;
import com.example.base.model.Prioridade;
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

    public ChamadoResponse cadastrarNovoChamado(Long usuarioId, ChamadoRequest chamadoRequest){

        Usuario usuario = adminRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNotFoundException("Usuario não encontrado"));

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

    public Page<ChamadoResponse> ListarChamadosAbertosUsuario (Long usuarioId, Pageable pageable){
        return chamadoRepository.findAllByUsuario_IdAndStatus(usuarioId, StatusChamado.ABERTO, pageable).map(this::toResponse);
    }

    public ChamadoResponse toResponse(Chamado chamado){
        AdminResponse adminResponse = new AdminResponse(chamado.getUsuario().getId(),chamado.getUsuario().getPapel(),chamado.getUsuario().getNome(),chamado.getUsuario().getEmail());
        
        return new ChamadoResponse(chamado.getId(), chamado.getTitulo(), chamado.getDescricao(), chamado.getStatus(),chamado.getPrioridade(), chamado.getCategoria(), adminResponse);
    }


}
