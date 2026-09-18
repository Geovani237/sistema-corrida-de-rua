package com.geovani237.corrida_de_rua.service;

import com.geovani237.corrida_de_rua.exception.NegocioException;
import com.geovani237.corrida_de_rua.entity.CorredorEntity;
import com.geovani237.corrida_de_rua.entity.Corrida;
import com.geovani237.corrida_de_rua.repository.old.CorredorRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class CorredorService {

    private CorredorRepository corredorRepository;

    public CorredorService() {
        this.corredorRepository = new CorredorRepository();
    }

    public CorredorEntity cadastrar(CorredorEntity corredorEntity) {
//        if (corredorEntity.getIdade() < 5 || corredorEntity.getCategoria() == null) {
//            throw new NegocioException("Dados obrigátórios não informados ou errados, corrija!");
//        }

        try {
            return corredorRepository.cadastrar(corredorEntity);
        } catch (Exception e) {
            throw new  RuntimeException("Erro ao cadastra corredor", e);
        }
    }

    public List<CorredorEntity> listarTodos() {
        return corredorRepository.listarTodos();
    }

    public void atualizarCorrida(Integer numeroPeito, Corrida corrida) {
        CorredorEntity corredorEntity = corredorRepository.buscarPorId(numeroPeito);
//        if (corredorEntity != null) {
//            corredorEntity.setCorrida(corrida);
//        } else {
//            throw new NegocioException("Corredor não encontrado");
//        }
    }

    public void retirarKit(Integer corredorId) {
        CorredorEntity corredorEntity = corredorRepository.retirarKit(corredorId);
        if (corredorEntity != null) {
            System.out.printf("Kit retirado para o corredor %d", corredorId);
        } else {
            throw new RuntimeException("Corredor não encontrado!");
        }
    }

    public void registrarChegada(LocalDateTime duracaoCorrida, Corrida corrida, CorredorEntity corredorEntity) {
        LocalDateTime inicioCorrida = corrida.getData();

        Duration duracao = Duration.between(inicioCorrida, duracaoCorrida);

        LocalTime tempoCorrida = LocalTime.of(duracao.toHoursPart(), duracao.toMinutesPart(), duracao.toSecondsPart());

        corredorEntity.setDuracaoCorrida(tempoCorrida);

        System.out.printf("Tempo de duração do corredor %s foi de %tT%n", corredorEntity.getNome(),tempoCorrida);
    }
}
