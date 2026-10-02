package com.geovani237.corrida_de_rua.service;

import com.geovani237.corrida_de_rua.dto.CorridaRequest;
import com.geovani237.corrida_de_rua.dto.CorridaResponse;
import com.geovani237.corrida_de_rua.entity.CorredorEntity;
import com.geovani237.corrida_de_rua.entity.CorridaEntity;
import com.geovani237.corrida_de_rua.exception.DadosInvalidosException;
import com.geovani237.corrida_de_rua.exception.ErroSistemicoException;
import com.geovani237.corrida_de_rua.mapper.CorridaMapper;
import com.geovani237.corrida_de_rua.repository.CorridaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CorridaService {

    private final CorridaRepository corridaRepository;

    private final CorridaMapper corridaMapper;


    public CorridaResponse cadastrar(CorridaRequest corridaRequest) {

        try {
            CorridaEntity corridaEntity = corridaMapper.toEntity(corridaRequest);

            corridaRepository.save(corridaEntity);

            return corridaMapper.toResponse(corridaEntity);

        } catch (Exception e) {
            throw new ErroSistemicoException("Erro ao cadastra Corrida");
        }

    }

    public List<CorridaResponse> listarCorridas() {
        return corridaRepository.findAll().stream()
                .map(corridaMapper::toResponse)
                .collect(java.util.stream.Collectors.toList());
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
