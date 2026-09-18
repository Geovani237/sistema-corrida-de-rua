package com.geovani237.corrida_de_rua.controller;

import com.geovani237.corrida_de_rua.api.CorredorApi;
import com.geovani237.corrida_de_rua.dto.CorredorRequest;
import com.geovani237.corrida_de_rua.dto.CorredorResponse;
import com.geovani237.corrida_de_rua.entity.CorredorEntity;
import com.geovani237.corrida_de_rua.entity.Corrida;
import com.geovani237.corrida_de_rua.service.CorredorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/corredores")
public class CorredorController implements CorredorApi {

    private final CorredorService corredorService;

    @Override
    @PostMapping
    public ResponseEntity<Long> cadastrar(CorredorRequest corredorRequest) {
//        corredorService.cadastrar(corredor);
//        return corredor.getNumeroPeito();
        return null;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CorredorResponse>> listarTodos() {
//        return corredorService.listarTodos();
        return null;
    }


    public void atualizarCorrida(Integer numeroPeito, Corrida corrida) {
        corredorService.atualizarCorrida(numeroPeito, corrida);
    }

    public void retirarKit(Integer corredorId) {
        corredorService.retirarKit(corredorId);
    }

    public void registarCorrida(LocalDateTime duracaoCorrida, Corrida corrida, CorredorEntity corredorEntity) {
        corredorService.registrarChegada(duracaoCorrida, corrida, corredorEntity);
    }
}
