package com.geovani237.corrida_de_rua.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record CorridaResponse(

        @Schema(description = "ID da corrida", example = "1")
        Long id,

        @Schema(description = "Distância da corrida", example = "10.0")
        Double distancia,

        @Schema(description = "Data da corrida", example = "2023-06-01T10:00:00")
        LocalDateTime data,

        @Schema(description = "Local da corrida", example = "Praça do seu Jorge")
        String local
) {
}
