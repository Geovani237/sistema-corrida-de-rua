package com.geovani237.corrida_de_rua.service;

import com.geovani237.corrida_de_rua.exception.NegocioException;
import com.geovani237.corrida_de_rua.model.Corredor;
import com.geovani237.corrida_de_rua.model.Corrida;
import com.geovani237.corrida_de_rua.repository.CorredorRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CorredorService {

    private CorredorRepository corredorRepository;

    public CorredorService() {
        this.corredorRepository = new CorredorRepository();
    }

    public Corredor cadastrar(Corredor corredor) {
        if (corredor.getIdade() < 5 || corredor.getCategoria().isEmpty()) {
            throw new NegocioException("Dados obrigátórios não informados ou errados, corrija!");
        }

        try {
            return corredorRepository.cadastrar(corredor);
        } catch (Exception e) {
            throw new  RuntimeException("Erro ao cadastra corredor", e);
        }
    }

    public List<Corredor> listarTodos() {
        return corredorRepository.listarTodos();
    }

    public void atualizarCorrida(Integer numeroPeito, Corrida corrida) {
        Corredor corredor = corredorRepository.buscarPorId(numeroPeito);
        if (corredor != null) {
            corredor.setCorrida(corrida);
        } else {
            throw new NegocioException("Corredor não encontrado");
        }
    }

    public void retirarKit(Integer corredorId) {
        Corredor corredor = corredorRepository.retirarKit(corredorId);
        if (corredor != null) {
            System.out.printf("Kit retirado para o corredor %d", corredorId);
        } else {
            throw new RuntimeException("Corredor não encontrado!");
        }
    }

    public void registrarChegada(LocalDateTime duracaoCorrida, Corrida corrida) {
        LocalDateTime inicioCorrida = corrida.getData();

        Duration duracao = Duration.between(inicioCorrida, duracaoCorrida);

        LocalTime tempoCorrida = LocalTime.of(duracao.toHoursPart(), duracao.toMinutesPart());

        System.out.println("Tempo de duração da corrida foi de " + tempoCorrida);
    }
}
