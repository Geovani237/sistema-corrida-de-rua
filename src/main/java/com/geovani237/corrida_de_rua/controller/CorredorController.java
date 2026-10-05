package com.geovani237.corrida_de_rua.controller;

import com.geovani237.corrida_de_rua.api.CorredorApi;
import com.geovani237.corrida_de_rua.dto.CorredorRequest;
import com.geovani237.corrida_de_rua.dto.CorredorResponse;
import com.geovani237.corrida_de_rua.dto.CorridaResponse;
import com.geovani237.corrida_de_rua.entity.CorredorEntity;
import com.geovani237.corrida_de_rua.entity.CorridaEntity;
import com.geovani237.corrida_de_rua.service.CorredorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/corredores")
public class CorredorController implements CorredorApi {

    private final CorredorService corredorService;

    @Override
    @PostMapping
    public ResponseEntity<CorredorResponse> cadastrar(@RequestBody @Valid CorredorRequest corredorRequest) {
        CorredorResponse corredor = corredorService.cadastrar(corredorRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(corredor);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CorredorResponse>> listarTodos() {
        return corredorService.listarTodos().isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(corredorService.listarTodos());
    }

    @Override
    @PutMapping("/{numeroPeito}/corridas/{corridaId}")
    public ResponseEntity<CorredorResponse> atualizarCorrida(@PathVariable Long numeroPeito, @PathVariable Long corridaId) {
        CorredorResponse corredor = corredorService.atualizarCorrida(numeroPeito, corridaId);

        return ResponseEntity.ok(corredor);
    }

//    public void retirarKit(Integer corredorId) {
//        corredorService.retirarKit(corredorId);
//    }
//
//    public void registarCorrida(LocalDateTime duracaoCorrida, CorridaEntity corridaEntity, CorredorEntity corredorEntity) {
//        corredorService.registrarChegada(duracaoCorrida, corridaEntity, corredorEntity);
//    }
}
