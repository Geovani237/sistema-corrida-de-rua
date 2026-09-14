package com.geovani237.corrida_de_rua.controller;

import com.geovani237.corrida_de_rua.model.Corredor;
import com.geovani237.corrida_de_rua.model.Corrida;
import com.geovani237.corrida_de_rua.service.CorredorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@RestController
public class CorredorController {

    private final CorredorService corredorService;

    public Integer cadastrar(Corredor corredor) {
        corredorService.cadastrar(corredor);
        return corredor.getNumeroPeito();
    }

    public List<Corredor> listarTodos() {
        return corredorService.listarTodos();
    }


    public void atualizarCorrida(Integer numeroPeito, Corrida corrida) {
        corredorService.atualizarCorrida(numeroPeito, corrida);
    }

    public void retirarKit(Integer corredorId) {
        corredorService.retirarKit(corredorId);
    }

    public void registarCorrida(LocalDateTime duracaoCorrida, Corrida corrida, Corredor corredor) {
        corredorService.registrarChegada(duracaoCorrida, corrida, corredor);
    }
}
