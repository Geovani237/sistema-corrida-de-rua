package com.geovani237.corrida_de_rua.dto;

import com.geovani237.corrida_de_rua.enums.CategoriaEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CorredorResponse (

        @Schema(description = "${schema.corredor_response.numero_peito}", example = "101")
        Long numeroPeito,
        @Schema(description = "${schema.corredor_response.nome}", example = "${example.corredor.nome}")
        String nome,
        @Schema(description = "${schema.corredor_response.idade}", example = "28")
        Integer idade,
        @Schema(description = "${schema.corredor_response.categoria}", example = "AMADOR")
        CategoriaEnum categoria,
        @Schema(description = "${schema.corredor_response.corrida_ids}", example = "[1]")
        List<Long> corridaIds
){


}
