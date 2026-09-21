package com.geovani237.corrida_de_rua.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record CorridaResponse(

        Long id,

        Double distancia,

        LocalDateTime data,

        String local
) {
}
