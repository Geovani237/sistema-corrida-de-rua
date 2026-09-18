package com.geovani237.corrida_de_rua.dto;

import com.geovani237.corrida_de_rua.enums.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalTime;


public record CorredorRequest (

        @Schema(description = "Nome completo do corredor", example = "Maria da Silva", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Nome obrigatório")
        String nome,

        @Schema(description = "Idade do corredor em anos", example = "28", minimum = "12", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Idade obrigatória")
        @Min(value = 12, message = "Idade mínima é 12 anos")
        Integer idade,

        @Schema(description = "Categoria do corredor", example = "AMADOR", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Categoria obrigatória")
        Categoria categoria,

        @Schema(description = "Identificador da corrida à qual o corredor será vinculado", example = "1", minimum = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Id da corrida é obrigatório")
        @Positive(message = "Id da corrida deve ser positivo")
        Long corridaId
){


}
