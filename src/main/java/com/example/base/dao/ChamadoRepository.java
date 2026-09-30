package com.example.base.dao;

import com.example.base.model.Chamado;
import com.example.base.model.StatusChamado;
import com.example.base.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long>  {

    Page<Chamado> findAllByUsuario_IdAndStatus(Long usuarioId, StatusChamado status, Pageable pageable);
}
