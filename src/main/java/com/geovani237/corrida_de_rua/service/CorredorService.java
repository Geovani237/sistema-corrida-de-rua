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
                    () -> new CorridaNaoEncontradoException("error.corrida.nao_encontrada")));

        try {
            corredorRepository.save(corredorEntity);
            return corredorMapper.toResponse(corredorEntity);
        }
        catch (Exception e) {
            log.error("error.corredor.cadastrar", e);
            throw new ErroSistemicoException("error.corredor.cadastrar");
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
                () -> new CorredorNaoEncontradoException("error.corredor.nao_encontrado"));

        if (corredorEntity.getCorridas().stream().anyMatch(corrida -> corrida.getId().equals(corridaId))) {
            throw new ErroSistemicoException("error.corredor.atualizar_corrida");
        } else  {
            CorridaEntity corridaEntity = corridaRepository.findById(corridaId).orElseThrow(
                    () -> new CorridaNaoEncontradoException("error.corrida.nao_encontrada"));
            corredorEntity.getCorridas().add(corridaEntity);
            corredorRepository.save(corredorEntity);

            return corredorMapper.toResponse(corredorEntity);
        }
    }

    @Transactional
    public void retirarKit(Long corredorId) {
        CorredorEntity corredorEntity = corredorRepository.findById(corredorId).orElseThrow(
                () -> new CorredorNaoEncontradoException("error.corredor.nao_encontrado"));

        corredorEntity.setStatusPagamento(true);
        // ideal ter um try catch
        corredorRepository.save(corredorEntity);

    }

    @Transactional
    public void registrarChegada(Long corridaId, Long corredorId) {

        CorredorEntity corredorEntity = corredorRepository.findById(corredorId).orElseThrow(
                () -> new CorredorNaoEncontradoException("error.corredor.nao_encontrado"));

        CorridaEntity corridaEntity = corredorEntity.getCorridas().stream()
                .filter(corrida -> corrida.getId().equals(corridaId))
                .findFirst()
                .orElseThrow(() -> new CorridaNaoEncontradoException("error.corrida.nao_encontrada"));

        LocalDateTime inicioCorrida = corridaEntity.getData();
        LocalDateTime chegadaCorrida = LocalDateTime.now();
        Duration duracao = Duration.between(inicioCorrida, chegadaCorrida);
        corredorEntity.setDuracaoCorrida((double) duracao.toSeconds());
        corredorRepository.save(corredorEntity);

    }
}
