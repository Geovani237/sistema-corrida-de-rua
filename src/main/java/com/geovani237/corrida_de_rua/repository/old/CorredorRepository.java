package com.geovani237.corrida_de_rua.repository.old;

import com.geovani237.corrida_de_rua.entity.CorredorEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CorredorRepository {

    private static final Map<Integer, CorredorEntity> dbCorredor = new HashMap<>();
    private static Integer numeroPeito = 1;

    public CorredorEntity cadastrar(CorredorEntity corredorEntity) {
//        corredorEntity.setNumeroPeito(numeroPeito++);
//        dbCorredor.put(corredorEntity.getNumeroPeito(), corredorEntity);
        return corredorEntity;
    }

    public List<CorredorEntity> listarTodos() {
        return new ArrayList<>(dbCorredor.values());
    }

    public void atualizar(CorredorEntity corredorEntity) {
//        dbCorredor.put(corredorEntity.getNumeroPeito(), corredorEntity);
    }

    public CorredorEntity buscarPorId(Integer numeroPeito) {
        return dbCorredor.get(numeroPeito);
    }

    public CorredorEntity retirarKit(Integer corredorId) {
        return buscarPorId(corredorId);
    }

//    public void registrarChegada(Integer tempoDeChegada) {
//        return tempoDeChegada;
//    }
}
