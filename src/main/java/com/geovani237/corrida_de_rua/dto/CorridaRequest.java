package com.geovani237.corrida_de_rua.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;


public record CorridaRequest(

        @Schema(description = "Distância da corrida", example = "10.0", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Distância obrigatória")
        @Positive(message = "Distância deve ser positiva")
        Double distancia,

        @Schema(description = "Data da corrida", example = "2023-06-01T10:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Data obrigatória")
        LocalDateTime data,

        @Schema(description = "Local da corrida", example = "Praça do seu Jorge", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Local obrigatório")
        String local
){


}
