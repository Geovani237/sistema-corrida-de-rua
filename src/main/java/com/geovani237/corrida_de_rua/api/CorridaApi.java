package com.geovani237.corrida_de_rua.api;

import com.geovani237.corrida_de_rua.dto.CorridaRequest;
import com.geovani237.corrida_de_rua.dto.CorridaResponse;
import com.geovani237.corrida_de_rua.dto.ErrorResponse;
import com.geovani237.corrida_de_rua.dto.ResultadoPorCategoriaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "tag.corrida", description = "")
public interface CorridaApi {

    @Operation(
            operationId = "corridaCadastrar",
            summary = "",
            description = ""
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CorridaResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    ResponseEntity<CorridaResponse> cadastrar(
            @RequestBody(
                    required = true,
                    description = "",
                    content = @Content(schema = @Schema(implementation = CorridaRequest.class))
            )
            CorridaRequest corridaRequest
    );

    @Operation(
            operationId = "corridaListarCorridas",
            summary = "",
            description = ""
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CorridaResponse.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = ""
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    ResponseEntity<List<CorridaResponse>> listarCorridas();

    @Operation(
            operationId = "corridaResultadoPorCategoria",
            summary = "",
            description = ""
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResultadoPorCategoriaResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<ResultadoPorCategoriaResponse> resultadoPorCategoria(
            @PathVariable("corridaId") Long corridaId
    );
}
