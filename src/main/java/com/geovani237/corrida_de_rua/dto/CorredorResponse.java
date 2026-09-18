package com.geovani237.corrida_de_rua.dto;

import com.geovani237.corrida_de_rua.entity.Corrida;
import com.geovani237.corrida_de_rua.enums.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

public record CorredorResponse (

        @Schema(description = "Número de peito atribuído ao corredor", example = "101")
        Long numeroPeito,
        @Schema(description = "Nome completo do corredor", example = "Maria da Silva")
        String nome,
        @Schema(description = "Idade do corredor em anos", example = "28")
        Integer idade,
        @Schema(description = "Categoria do corredor", example = "AMADOR")
        Categoria categoria,
        @Schema(description = "Identificadores das corridas do corredor", example = "[1]")
        List<Long> corridaIds
){


}
