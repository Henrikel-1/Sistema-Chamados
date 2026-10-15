package com.example.base.dao;

import com.example.base.model.Categoria;
import com.example.base.model.Chamado;
import com.example.base.model.StatusChamado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    Page<Chamado> findAllByUsuario_Id(Long usuarioId, Pageable pageable);
    Page<Chamado> findAllByUsuario_IdAndStatus(Long usuarioId, StatusChamado status, Pageable pageable);
    Page<Chamado> findAllByStatus(StatusChamado status, Pageable pageable);
    Page<Chamado> findAllByCategoria(Categoria categoria, Pageable pageable);
    Page<Chamado> findAllByStatusAndCategoria(StatusChamado status, Categoria categoria, Pageable pageable);
    @Query("SELECT c FROM Chamado c WHERE c.usuario.id = :usuarioId " +
            "AND (LOWER(c.titulo) LIKE LOWER(CONCAT('%', :busca, '%')) " +
            "OR LOWER(c.descricao) LIKE LOWER(CONCAT('%', :busca, '%')))")
    Page<Chamado> buscarDoUsuario(@Param("usuarioId") Long usuarioId,
                                  @Param("busca") String busca,
                                  Pageable pageable);

    @Query("SELECT c FROM Chamado c WHERE c.usuario.id = :usuarioId AND c.status = :status " +
            "AND (LOWER(c.titulo) LIKE LOWER(CONCAT('%', :busca, '%')) " +
            "OR LOWER(c.descricao) LIKE LOWER(CONCAT('%', :busca, '%')))")

    Page<Chamado> buscarDoUsuarioComStatus(@Param("usuarioId") Long usuarioId,
                                           @Param("status") StatusChamado status,
                                           @Param("busca") String busca,
                                           Pageable pageable);
}