package com.geovani237.corrida_de_rua.api;

import com.geovani237.corrida_de_rua.dto.CorredorRequest;
import com.geovani237.corrida_de_rua.dto.CorredorResponse;
import com.geovani237.corrida_de_rua.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name = "tag.corredor", description = "")
public interface CorredorApi {

    @Operation(
            operationId = "corredorCadastrar",
            summary = "",
            description = ""
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CorredorResponse.class)
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
                    responseCode = "404",
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
    ResponseEntity<CorredorResponse> cadastrar(
            @RequestBody(
                    required = true,
                    description = "",
                    content = @Content(schema = @Schema(implementation = CorredorRequest.class))
            )
            CorredorRequest corredorRequest
    );

    @Operation(
            operationId = "corredorListarTodos",
            summary = "",
            description = ""
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CorredorResponse.class))
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
    ResponseEntity<List<CorredorResponse>> listarTodos();

    @Operation(
            operationId = "corredorAtualizarCorrida",
            summary = "",
            description = ""
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CorredorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
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
    ResponseEntity<CorredorResponse> atualizarCorrida(
            @Parameter(description = "", example = "101", required = true)
            @PathVariable("numeroPeito") Long numeroPeito,
            @Parameter(description = "", example = "1", required = true)
            @PathVariable("corridaId") Long corridaId
    );

    @Operation(
            operationId = "corredorRetirarKit",
            summary = "",
            description = ""
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(
                    responseCode = "404",
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
    ResponseEntity<Void> retirarKit(
            @Parameter(description = "", example = "101", required = true)
            @PathVariable("corredorId") Long corredorId
    );

    @Operation(
            operationId = "corredorRegistrarChegada",
            summary = "",
            description = ""
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(
                    responseCode = "404",
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
    ResponseEntity<Void> registarCorrida(
            @Parameter(description = "", example = "1", required = true)
            @PathVariable("corridaId") Long corridaId,
            @Parameter(description = "", example = "101", required = true)
            @PathVariable("corredorId") Long corredorId
    );
}
