package com.geovani237.corrida_de_rua.service;

import com.geovani237.corrida_de_rua.dto.CorredorRequest;
import com.geovani237.corrida_de_rua.dto.CorredorResponse;
import com.geovani237.corrida_de_rua.entity.CorredorEntity;
import com.geovani237.corrida_de_rua.entity.CorridaEntity;
import com.geovani237.corrida_de_rua.exception.CorredorNaoEncontradoException;
import com.geovani237.corrida_de_rua.exception.CorridaNaoEncontradoException;
import com.geovani237.corrida_de_rua.exception.ErroSistemicoException;
import com.geovani237.corrida_de_rua.mapper.CorredorMapper;
import com.geovani237.corrida_de_rua.repository.CorredorRepository;
import com.geovani237.corrida_de_rua.repository.CorridaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CorredorService {

    private final CorredorRepository corredorRepository;

    private final CorridaRepository corridaRepository;

    private final CorredorMapper corredorMapper;


    public CorredorResponse cadastrar(CorredorRequest corredorRequest) {

        try {
            CorredorEntity corredorEntity = corredorMapper.toEntity(corredorRequest);

            corredorEntity.getCorridas().add(corridaRepository.findById(corredorRequest.corridaId()).orElseThrow(
                    () -> new CorridaNaoEncontradoException("Corrida não encontrada")));

            corredorRepository.save(corredorEntity);
            return corredorMapper.toResponse(corredorEntity);
        } catch (Exception e) {
            throw new ErroSistemicoException("Erro ao cadastra corredor");
        }
    }

    public List<CorredorResponse> listarTodos() {
        return corredorRepository.findAll().stream()
                .map(corredorMapper::toResponse)
                .collect(Collectors.toList());
    }

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

    public void retirarKit(Long corredorId) {
//        CorredorEntity corredorEntity = corredorRepository.retirarKit(corredorId);
//        if (corredorEntity != null) {
//            System.out.printf("Kit retirado para o corredor %d", corredorId);
//        } else {
//            throw new CorredorNaoEncontradoException("Corredor não encontrado!");
//        }
    }

    public void registrarChegada(LocalDateTime duracaoCorrida, CorridaEntity corridaEntity, CorredorEntity corredorEntity) {
        LocalDateTime inicioCorrida = corridaEntity.getData();

        Duration duracao = Duration.between(inicioCorrida, duracaoCorrida);

        LocalTime tempoCorrida = LocalTime.of(duracao.toHoursPart(), duracao.toMinutesPart(), duracao.toSecondsPart());

//        corredorEntity.setDuracaoCorrida(tempoCorrida);

        System.out.printf("Tempo de duração do corredor %s foi de %tT%n", corredorEntity.getNome(),tempoCorrida);
    }
}
