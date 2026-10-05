package com.geovani237.corrida_de_rua.mapper;

import com.geovani237.corrida_de_rua.dto.CorredorRequest;
import com.geovani237.corrida_de_rua.dto.CorredorResponse;
import com.geovani237.corrida_de_rua.dto.CorridaRequest;
import com.geovani237.corrida_de_rua.dto.CorridaResponse;
import com.geovani237.corrida_de_rua.entity.CorredorEntity;
import com.geovani237.corrida_de_rua.entity.CorridaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CorridaMapper {

    /*

    entity
    private Double distancia;
    private LocalDateTime data;
    private String local;
    private List<CorredorEntity> corredores;

    ---
    request
    Double distancia,
    LocalDateTime data,
    String local
    ----
    response
    Long id,
    Double distancia,
    LocalDateTime data,
    String local,
    List<Long> corredores
     */

    CorridaEntity toEntity(CorridaRequest request);

    @Mapping(target = "corredores", expression = "java(mapCorredoresToIds(entity.getCorredores()))")
    CorridaResponse toResponse(CorridaEntity entity);


    default List<Long> mapCorredoresToIds(List<CorredorEntity> corredores) {
        if (corredores == null) {
            return null;
        }
        return corredores.stream()
                .map(CorredorEntity::getId)
                .toList();
    }
}
