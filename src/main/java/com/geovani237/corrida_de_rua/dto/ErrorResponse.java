package com.geovani237.corrida_de_rua.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(name = "ErrorResponse", description = "Estrutura padrão das mensagens de erro da API")
public record ErrorResponse(

        @Schema(description = "Data e hora em que o erro ocorreu", example = "2026-09-18T12:31:32Z")
        Instant timestamp,

        @Schema(description = "Código HTTP do erro", example = "400")
        Integer status,

        @Schema(description = "Descrição resumida do erro", example = "Bad Request")
        String error,

        @Schema(description = "Detalhes do erro", example = "Idade mínima é 12 anos")
        String message,

        @Schema(description = "Caminho da requisição que gerou o erro", example = "/api/v1/corredores")
        String path
) {
}
