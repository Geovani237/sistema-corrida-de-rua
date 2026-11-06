package com.geovani237.corrida_de_rua.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;


public record CorridaRequest(

        @Schema(description = "${schema.corrida_request.distancia}", example = "10.0", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{validation.corrida.distancia.obrigatoria}")
        @Positive(message = "{validation.corrida.distancia.positiva}")
        Double distancia,

        @Schema(description = "${schema.corrida_request.data}", example = "2023-06-01T10:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{validation.corrida.data.obrigatoria}")
        LocalDateTime data,

        @Schema(description = "${schema.corrida_request.local}", example = "${example.corrida.local}", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{validation.corrida.local.obrigatorio}")
        String local
){


}
