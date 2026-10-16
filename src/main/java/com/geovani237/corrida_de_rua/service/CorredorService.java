package com.geovani237.corrida_de_rua.service;

import com.geovani237.corrida_de_rua.dto.CorredorRequest;
import com.geovani237.corrida_de_rua.dto.CorredorResponse;
import com.geovani237.corrida_de_rua.dto.CorridaRequest;
import com.geovani237.corrida_de_rua.entity.CorredorEntity;
import com.geovani237.corrida_de_rua.entity.CorridaEntity;
import com.geovani237.corrida_de_rua.exception.CorredorNaoEncontradoException;
import com.geovani237.corrida_de_rua.exception.CorridaNaoEncontradoException;
import com.geovani237.corrida_de_rua.exception.ErroSistemicoException;
import com.geovani237.corrida_de_rua.mapper.CorredorMapper;
import com.geovani237.corrida_de_rua.repository.CorredorRepository;
import com.geovani237.corrida_de_rua.repository.CorridaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CorredorService {

    private final CorredorRepository corredorRepository;

    private final CorridaRepository corridaRepository;

    private final CorredorMapper corredorMapper;

    @Transactional
    public CorredorResponse cadastrar(CorredorRequest corredorRequest) {

            CorredorEntity corredorEntity = corredorMapper.toEntity(corredorRequest);

            corredorEntity.getCorridas().add(corridaRepository.findById(corredorRequest.corridaId()).orElseThrow(
                    () -> new CorridaNaoEncontradoException("Corrida não encontrada")));

        try {
            corredorRepository.save(corredorEntity);
            return corredorMapper.toResponse(corredorEntity);
        }
        catch (Exception e) {
            log.error("Erro ao cadastrar corredor", e);
            throw new ErroSistemicoException("Erro ao cadastra corredor");
        }
    }

    public List<CorredorResponse> listarTodos() {
        return corredorRepository.findAll().stream()
                .map(corredorMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CorredorResponse atualizarCorrida(Long numeroPeito, Long corridaId) {
        CorredorEntity corredorEntity = corredorRepository.findById(numeroPeito).orElseThrow(
                () -> new CorredorNaoEncontradoException("Corredor não encontrado"));

        if (corredorEntity.getCorridas().stream().anyMatch(corrida -> corrida.getId().equals(corridaId))) {
            throw new ErroSistemicoException("Corrida já cadastrada para o corredor");
        } else  {
            CorridaEntity corridaEntity = corridaRepository.findById(corridaId).orElseThrow(
                    () -> new CorridaNaoEncontradoException("Corrida não encontrada"));
            corredorEntity.getCorridas().add(corridaEntity);
            corredorRepository.save(corredorEntity);

            return corredorMapper.toResponse(corredorEntity);
        }
    }

    @Transactional
    public void retirarKit(Long corredorId) {
        CorredorEntity corredorEntity = corredorRepository.findById(corredorId).orElseThrow(
                () -> new CorredorNaoEncontradoException("Corredor não encontrado"));

        corredorEntity.setStatusPagamento(true);
        // ideal ter um try catch
        corredorRepository.save(corredorEntity);

    }

    @Transactional
    public void registrarChegada(Long corridaId, Long corredorId) {

        CorredorEntity corredorEntity = corredorRepository.findById(corredorId).orElseThrow(
                () -> new CorredorNaoEncontradoException("Corredor não encontrado"));

        CorridaEntity corridaEntity = corredorEntity.getCorridas().stream()
                .filter(corrida -> corrida.getId().equals(corridaId))
                .findFirst()
                .orElseThrow(() -> new CorridaNaoEncontradoException("Corrida não encontrada"));

        LocalDateTime inicioCorrida = corridaEntity.getData();
        LocalDateTime chegadaCorrida = LocalDateTime.now();
        Duration duracao = Duration.between(inicioCorrida, chegadaCorrida);
        corredorEntity.setDuracaoCorrida((double) duracao.toSeconds());
        corredorRepository.save(corredorEntity);

    }
}
