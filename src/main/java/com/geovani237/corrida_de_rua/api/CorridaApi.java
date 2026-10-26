package com.geovani237.corrida_de_rua.api;

import com.geovani237.corrida_de_rua.dto.CorridaRequest;
import com.geovani237.corrida_de_rua.dto.CorridaResponse;
import com.geovani237.corrida_de_rua.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Corrida", description = "Endpoints para cadastrar e consultar corridas")
public interface CorridaApi {

    @Operation(
            summary = "Cadastrar uma corrida",
            description = "Cadastra uma corrida com distância positiva, data e local informados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Corrida cadastrada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CorridaResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "distancia": 10.0,
                                      "data": "2026-11-01T08:00:00",
                                      "local": "Parque Central",
                                      "corredores": []
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da corrida inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao cadastrar a corrida",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    ResponseEntity<CorridaResponse> cadastrar(
            @RequestBody(
                    required = true,
                    description = "Distância, data e local da corrida",
                    content = @Content(schema = @Schema(implementation = CorridaRequest.class))
            )
            CorridaRequest corridaRequest
    );

    @Operation(
            summary = "Listar corridas",
            description = "Retorna as corridas cadastradas. Se não houver corridas, a resposta será 204 sem corpo."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Corridas retornadas com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CorridaResponse.class)),
                            examples = @ExampleObject(value = """
                                    [
                                      {
                                        "id": 1,
                                        "distancia": 10.0,
                                        "data": "2026-11-01T08:00:00",
                                        "local": "Parque Central",
                                        "corredores": []
                                      }
                                    ]
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Não há corridas cadastradas"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao listar corridas",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    ResponseEntity<List<CorridaResponse>> listarCorridas();
}
