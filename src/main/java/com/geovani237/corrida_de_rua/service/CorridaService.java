package com.geovani237.corrida_de_rua.service;

import com.geovani237.corrida_de_rua.exception.NegocioException;
import com.geovani237.corrida_de_rua.model.Corrida;
import com.geovani237.corrida_de_rua.repository.CorridaRepository;

import java.util.List;

public class CorridaService {

    private final CorridaRepository corridaRepository;

    public CorridaService() {
        this.corridaRepository = new CorridaRepository();
    }

    public Corrida cadastrar(Corrida corrida) {
        if (corrida.getData() == null || corrida.getLocal() == null || corrida.getDistancia() == null) {
            throw new NegocioException("Não foi possível cadastrar a corrida, há algum campo não preenchido");
        }

        try {
            System.out.println("Inscrições foram abertas!");

            return corridaRepository.cadastrar(corrida);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastra Corrida", e);
        }


    }

    public List<Corrida> listarCorridas() {
        return corridaRepository.listarCorridas();
    }
}
