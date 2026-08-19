package com.geovani237.corrida_de_rua;

import com.geovani237.corrida_de_rua.controller.CorredorController;
import com.geovani237.corrida_de_rua.controller.CorridaController;
import com.geovani237.corrida_de_rua.model.Categoria;
import com.geovani237.corrida_de_rua.model.Corredor;
import com.geovani237.corrida_de_rua.model.Corrida;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Jornada_4 {
    public static void main(String[] args) {
        CorridaController corridaController = new CorridaController();
        CorredorController corredorController = new CorredorController();

        Corrida corrida = new Corrida();
        corrida.setData(LocalDateTime.now());
        corrida.setDistancia(5.0);
        corrida.setLocal("Praça do seu Zé");

        Corredor corredor = new Corredor();
        corredor.setCategoria(Categoria.AMADOR);
        corredor.setIdade(20);
        corredor.setNome("Rodrigo");

//        Corredor corredor2 = new Corredor();
//        corredor2.setCategoria("Competitivo");
//        corredor2.setIdade(25);
//        corredor2.setNome("Alex");
//        corredor2.setStatusPagamento(true);

        corridaController.cadastrar(corrida);

        corredorController.cadastrar(corredor);
//        corredorController.cadastrar(corredor2);

        corredorController.atualizarCorrida(1, corrida);
//        corredorController.atualizarCorrida(2, corrida);

        corredorController.listarTodos().forEach(System.out::println);
        System.out.println("-----------------");

        LocalDateTime tempoCorrida = LocalDateTime.of(2026,8,19,22,5,10);

        corredorController.registarCorrida(tempoCorrida, corrida, corredor);

        System.out.println("-----------------");
        corredorController.listarTodos().forEach(System.out::println);

    }
}
