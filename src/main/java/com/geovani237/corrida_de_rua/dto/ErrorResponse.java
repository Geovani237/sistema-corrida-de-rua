package com.geovani237.corrida_de_rua.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(name = "ErrorResponse", description = "${schema.error_response.description}")
public record ErrorResponse(

        @Schema(description = "${schema.error_response.timestamp}", example = "2026-09-18T12:31:32Z")
        Instant timestamp,

        @Schema(description = "${schema.error_response.status}", example = "400")
        Integer status,

        @Schema(description = "${schema.error_response.error}", example = "${example.error_response.error}")
        String error,

        @Schema(description = "${schema.error_response.message}", example = "${example.error_response.message}")
        String message,

        @Schema(description = "${schema.error_response.path}", example = "/api/v1/corredores")
        String path
) {
}
