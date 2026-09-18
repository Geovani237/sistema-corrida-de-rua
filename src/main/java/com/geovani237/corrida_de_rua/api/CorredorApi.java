package com.geovani237.corrida_de_rua.api;

import com.geovani237.corrida_de_rua.dto.CorredorRequest;
import com.geovani237.corrida_de_rua.dto.CorredorResponse;
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

@Tag(name = "Corredor", description = "endpoint para gerenciar dados do corredor")
public interface CorredorApi {

    @Operation(
            summary = "Cadastrar um novo corredor",
            description = "Cadastra um corredor vinculado a uma corrida. A idade mínima permitida é 12 anos."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Corredor cadastrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "integer", format = "int64", example = "101")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados do corredor inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2026-09-18T12:31:32Z",
                                      "status": 400,
                                      "error": "Bad Request",
                                      "message": "Idade mínima é 12 anos",
                                      "path": "/api/v1/corredores"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Corrida não encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2026-09-18T12:31:32Z",
                                      "status": 404,
                                      "error": "Not Found",
                                      "message": "Corrida não encontrada",
                                      "path": "/api/v1/corredores"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no sistema",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2026-09-18T12:31:32Z",
                                      "status": 500,
                                      "error": "Internal Server Error",
                                      "message": "Ocorreu um erro inesperado",
                                      "path": "/api/v1/corredores"
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<Long> cadastrar(
            @RequestBody(
                    required = true,
                    description = "Dados necessários para o cadastro do corredor",
                    content = @Content(schema = @Schema(implementation = CorredorRequest.class))
            )
            CorredorRequest corredorRequest
    );


    @Operation(
            summary = "Listar corredores cadastrados",
            description = "Retorna todos os corredores cadastrados. Quando não houver registros, a lista será vazia."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Corredores retornados com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CorredorResponse.class)),
                            examples = @ExampleObject(value = """
                                    [
                                      {
                                        "numeroPeito": 101,
                                        "nome": "Maria da Silva",
                                        "idade": 28,
                                        "categoria": "AMADOR",
                                        "corridaIds": [1]
                                      }
                                    ]
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no sistema",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2026-09-18T12:31:32Z",
                                      "status": 500,
                                      "error": "Internal Server Error",
                                      "message": "Ocorreu um erro inesperado",
                                      "path": "/api/v1/corredores"
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<List<CorredorResponse>> listarTodos();
}
