package com.geovani237.corrida_de_rua.service;

import com.geovani237.corrida_de_rua.entity.CorredorEntity;
import com.geovani237.corrida_de_rua.entity.CorridaEntity;
import com.geovani237.corrida_de_rua.repository.CorredorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CorredorService {

    private CorredorRepository corredorRepository;

//    public CorredorService() {
//        this.corredorRepository = new CorredorRepository();
//    }

    public CorredorEntity cadastrar(CorredorEntity corredorEntity) {
//        if (corredorEntity.getIdade() < 5 || corredorEntity.getCategoria() == null) {
//            throw new DadosInvalidosException("Dados obrigátórios não informados ou errados, corrija!");
//        }

        try {
            return corredorRepository.save(corredorEntity);
        } catch (Exception e) {
            throw new  RuntimeException("Erro ao cadastra corredor", e);
        }
    }

    public List<CorredorEntity> listarTodos() {
        return corredorRepository.findAll();
    }

    public void atualizarCorrida(Integer numeroPeito, CorridaEntity corridaEntity) {
//        CorredorEntity corredorEntity = corredorRepository.buscarPorId(numeroPeito);
//        if (corredorEntity != null) {
//            corredorEntity.setCorrida(corrida);
//        } else {
//            throw new CorredorNaoEncontradoException("Corredor não encontrado");
//        }
    }

    public void retirarKit(Integer corredorId) {
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
