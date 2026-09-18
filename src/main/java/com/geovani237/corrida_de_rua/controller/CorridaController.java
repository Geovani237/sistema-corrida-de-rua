package com.geovani237.corrida_de_rua.controller;

import com.geovani237.corrida_de_rua.entity.CorredorEntity;
import com.geovani237.corrida_de_rua.entity.Corrida;
import com.geovani237.corrida_de_rua.service.CorridaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/corridas")
public class CorridaController {

    private final CorridaService corridaService;

    @PostMapping
    public Integer cadastrar(Corrida corrida) {
        corridaService.cadastrar(corrida);

        return corrida.getId();
    }

    @GetMapping
    public List<Corrida> listarCorridas() {
        return corridaService.listarCorridas();
    }

    @GetMapping("/resultado")
    public void resultadoPorCategoria(Corrida corrida, List<CorredorEntity> corredores) {
        corridaService.resultadoPorCategoria(corrida, corredores);
    }
}
