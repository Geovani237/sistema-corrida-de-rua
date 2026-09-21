package com.geovani237.corrida_de_rua.mapper;

import com.geovani237.corrida_de_rua.dto.CorredorRequest;
import com.geovani237.corrida_de_rua.dto.CorredorResponse;
import com.geovani237.corrida_de_rua.entity.CorredorEntity;
import com.geovani237.corrida_de_rua.entity.CorridaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CorredorMapper {

    /*
    Response
    Long numeroPeito,
    String nome,
    Integer idade,
    CategoriaEnum categoriaEnum,
    List<Long> corridaIds

    ----
    Entity
    private String nome;
    private Integer idade;
    private boolean statusPagamento;
    private Double duracaoCorrida;
    private CategoriaEnum categoriaEnum;
    private List<CorridaEntity> corridas;

    ----
    Request
    String nome,
    Integer idade,
    CategoriaEnum categoriaEnum,
    Long corridaId
     */
    CorredorEntity toEntity(CorredorRequest request);

    @Mapping(target = "numeroPeito", source = "id")
    CorredorResponse toResponse(CorredorEntity entity);

}
