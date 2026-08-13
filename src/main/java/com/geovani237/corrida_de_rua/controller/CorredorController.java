package com.geovani237.corrida_de_rua.controller;

import com.geovani237.corrida_de_rua.model.Corredor;
import com.geovani237.corrida_de_rua.model.Corrida;
import com.geovani237.corrida_de_rua.service.CorredorService;

import java.util.List;

public class CorredorController {

    private CorredorService corredorService;

    public CorredorController() {
        this.corredorService = new CorredorService();
    }

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
}
