package com.geovani237.corrida_de_rua.service;

import com.geovani237.corrida_de_rua.dto.CorridaRequest;
import com.geovani237.corrida_de_rua.dto.CorridaResponse;
import com.geovani237.corrida_de_rua.dto.CorredorResultadoResponse;
import com.geovani237.corrida_de_rua.dto.ResultadoPorCategoriaResponse;
import com.geovani237.corrida_de_rua.enums.CategoriaEnum;
import com.geovani237.corrida_de_rua.entity.CorredorEntity;
import com.geovani237.corrida_de_rua.entity.CorridaEntity;
import com.geovani237.corrida_de_rua.exception.CorridaNaoEncontradoException;
import com.geovani237.corrida_de_rua.exception.ErroSistemicoException;
import com.geovani237.corrida_de_rua.mapper.CorridaMapper;
import com.geovani237.corrida_de_rua.repository.CorridaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
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

    @Transactional(readOnly = true)
    public ResultadoPorCategoriaResponse resultadoPorCategoria(Long corridaId) {
        CorridaEntity corrida = corridaRepository.findById(corridaId)
                .orElseThrow(() -> new CorridaNaoEncontradoException("Corrida não encontrada"));

        List<CorredorEntity> corredores = corrida.getCorredores() == null
                ? List.of()
                : corrida.getCorredores();

        return new ResultadoPorCategoriaResponse(
                obterResultadoDaCategoria(corredores, CategoriaEnum.AMADOR),
                obterResultadoDaCategoria(corredores, CategoriaEnum.ELITE)
        );
    }

    private List<CorredorResultadoResponse> obterResultadoDaCategoria(
            List<CorredorEntity> corredores,
            CategoriaEnum categoria
    ) {
        return corredores.stream()
                .filter(java.util.Objects::nonNull)
                .filter(corredor -> corredor.getCategoria() == categoria)
                .filter(corredor -> corredor.getDuracaoCorrida() != null)
                .sorted(Comparator.comparing(CorredorEntity::getDuracaoCorrida))
                .map(corredor -> new CorredorResultadoResponse(
                        corredor.getId(),
                        corredor.getNome(),
                        corredor.getDuracaoCorrida()
                ))
                .toList();
    }
}
