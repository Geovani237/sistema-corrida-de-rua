package com.geovani237.corrida_de_rua.repository.old;

import com.geovani237.corrida_de_rua.entity.CorridaEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CorridaRepository {

    private static final Map<Integer, CorridaEntity> dbCorrida = new HashMap<>();
    private Integer idCorrida = 1;

    public CorridaEntity cadastrar(CorridaEntity corridaEntity) {
        corridaEntity.setId(idCorrida++);
        dbCorrida.put(corridaEntity.getId(), corridaEntity);

        return corridaEntity;
    }

    public List<CorridaEntity> listarCorridas(){
        return new ArrayList<>(dbCorrida.values());
    }
}
