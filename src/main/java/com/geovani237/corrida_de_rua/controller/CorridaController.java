package com.geovani237.corrida_de_rua.controller;

import com.geovani237.corrida_de_rua.model.Corredor;
import com.geovani237.corrida_de_rua.model.Corrida;
import com.geovani237.corrida_de_rua.service.CorridaService;

import java.util.List;

public class CorridaController {

    private CorridaService corridaService;

    public CorridaController() {
        this.corridaService = new CorridaService();
    }

    public Integer cadastrar(Corrida corrida) {
        corridaService.cadastrar(corrida);

        return corrida.getId();
    }

    public List<Corrida> listarCorridas() {
        return corridaService.listarCorridas();
    }

    public void resultadoPorCategoria(Corrida corrida, List<Corredor> corredores) {
        corridaService.resultadoPorCategoria(corrida, corredores);
    }
}
