package com.geovani237.corrida_de_rua.repository;

import com.geovani237.corrida_de_rua.model.Corredor;
import com.geovani237.corrida_de_rua.model.Corrida;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CorredorRepository {

    private static final Map<Integer, Corredor> dbCorredor = new HashMap<>();
    private static Integer numeroPeito = 1;

    public Corredor cadastrar(Corredor corredor) {
        corredor.setNumeroPeito(numeroPeito++);
        dbCorredor.put(corredor.getNumeroPeito(), corredor);
        return corredor;
    }

    public List<Corredor> listarTodos() {
        return new ArrayList<>(dbCorredor.values());
    }

    public void atualizar(Corredor corredor) {
        dbCorredor.put(corredor.getNumeroPeito(), corredor);
    }

    public Corredor buscarPorId(Integer numeroPeito) {
        return dbCorredor.get(numeroPeito);
    }

    public Corredor retirarKit(Integer corredorId) {
        return buscarPorId(corredorId);
    }

//    public void registrarChegada(Integer tempoDeChegada) {
//        return tempoDeChegada;
//    }
}
