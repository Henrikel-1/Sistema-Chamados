package com.example.base.mapper;

import com.example.base.dto.ChamadoRequest;
import com.example.base.model.Chamado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChamadoMapper {
    @Mapping(target = "status", constant = "ABERTO")
    Chamado toEntity(ChamadoRequest chamadoDto);
    ChamadoRequest toDto(Chamado chamado);
}
