package com.geovani237.corrida_de_rua.api;

import com.geovani237.corrida_de_rua.dto.CorridaRequest;
import com.geovani237.corrida_de_rua.dto.CorridaResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Corrida", description = "endpoint para gerenciar dados da corrida")
public interface CorridaApi {

    ResponseEntity<Long> cadastrar(CorridaRequest corridaRequest);

    ResponseEntity<List<CorridaResponse>> listarCorridas();
}
