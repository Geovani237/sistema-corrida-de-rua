package com.geovani237.corrida_de_rua.controller;

import com.geovani237.corrida_de_rua.api.CorridaApi;
import com.geovani237.corrida_de_rua.dto.CorridaRequest;
import com.geovani237.corrida_de_rua.dto.CorridaResponse;
import com.geovani237.corrida_de_rua.entity.CorredorEntity;
import com.geovani237.corrida_de_rua.entity.CorridaEntity;
import com.geovani237.corrida_de_rua.service.CorridaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/corridas")
public class CorridaController implements CorridaApi {

    private final CorridaService corridaService;

    @Override
    @PostMapping
    public ResponseEntity<Long> cadastrar(CorridaRequest corridaRequest) {
//        corridaService.cadastrar(corridaEntity);

//        return corridaEntity.getId();
        return null;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CorridaResponse>> listarCorridas() {
//        return corridaService.listarCorridas();
        return null;
    }

    @GetMapping("/resultado")
    public void resultadoPorCategoria(CorridaEntity corridaEntity, List<CorredorEntity> corredores) {
        corridaService.resultadoPorCategoria(corridaEntity, corredores);
    }
}
