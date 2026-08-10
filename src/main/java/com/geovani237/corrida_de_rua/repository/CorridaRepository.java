package com.geovani237.corrida_de_rua.repository;

import com.geovani237.corrida_de_rua.model.Corrida;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CorridaRepository {

    private static final Map<Integer, Corrida> dbCorrida = new HashMap<>();
    private Integer idCorrida = 1;

    public Corrida cadastrar(Corrida corrida) {
        corrida.setId(idCorrida++);
        dbCorrida.put(corrida.getId(), corrida);

        return corrida;
    }

    public List<Corrida> listarCorridas(){
        return new ArrayList<>(dbCorrida.values());
    }
}
