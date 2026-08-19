package com.geovani237.corrida_de_rua;

import com.geovani237.corrida_de_rua.controller.CorridaController;
import com.geovani237.corrida_de_rua.model.Corrida;
import com.geovani237.corrida_de_rua.repository.CorridaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Jornada_1 {
    public static void main(String[] args) {
        Corrida corrida = new Corrida();
        corrida.setData(LocalDateTime.now().plusDays(10));
        corrida.setDistancia(5.0);
        corrida.setLocal("Praça do seu Zé");

        Corrida corrida2 = new Corrida();
        corrida2.setData(LocalDateTime.now().plusDays(17));
        corrida2.setDistancia(10.0);
        corrida2.setLocal("Centro da cidade");

        CorridaController corridaController = new CorridaController();
        corridaController.cadastrar(corrida);
        corridaController.cadastrar(corrida2);

        corridaController.listarCorridas().forEach(System.out::println);

    }
}
