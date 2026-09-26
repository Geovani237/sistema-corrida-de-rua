package com.geovani237.corrida_de_rua.service;

import com.geovani237.corrida_de_rua.entity.CorredorEntity;
import com.geovani237.corrida_de_rua.entity.CorridaEntity;
import com.geovani237.corrida_de_rua.exception.DadosInvalidosException;
import com.geovani237.corrida_de_rua.repository.CorridaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CorridaService {

    private final CorridaRepository corridaRepository;

//    public CorridaService() {
//        this.corridaRepository = new CorridaRepository();
//    }

    public CorridaEntity cadastrar(CorridaEntity corridaEntity) {
        if (corridaEntity.getData() == null || corridaEntity.getLocal() == null || corridaEntity.getDistancia() == null) {
            throw new DadosInvalidosException("Não foi possível cadastrar a corrida, há algum campo não preenchido");
        }

        try {
            System.out.println("Inscrições foram abertas!");

            return corridaRepository.save(corridaEntity);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastra Corrida", e);
        }


    }

    public List<CorridaEntity> listarCorridas() {
//        return corridaRepository.listarCorridas();
        return null;
    }

    public void resultadoPorCategoria(CorridaEntity corridaEntity, List<CorredorEntity> corredores) {
        List<CorredorEntity> amador = new ArrayList<>();
        List<CorredorEntity> elite = new ArrayList<>();

//        corredores.forEach(corredor -> {
//            if (corredor.getDuracaoCorrida() != null) {
//                switch (corredor.getCategoria()) {
//                    case ELITE -> elite.add(corredor);
//                    case AMADOR -> amador.add(corredor);
//                    default -> System.out.println("Corredor sem categoria!");
//                }
//            }
//        });

        System.out.println("-AMADORES-");
        amador.forEach(System.out::println);
        System.out.println("------------------");
//        amador.sort();

        System.out.println("-ELITE-");
        elite.forEach(System.out::println);
        System.out.println("------------------");

    }
}
