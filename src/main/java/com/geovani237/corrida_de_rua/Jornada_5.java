package com.geovani237.corrida_de_rua;

import com.geovani237.corrida_de_rua.controller.CorredorController;
import com.geovani237.corrida_de_rua.controller.CorridaController;
import com.geovani237.corrida_de_rua.model.Categoria;
import com.geovani237.corrida_de_rua.model.Corredor;
import com.geovani237.corrida_de_rua.model.Corrida;

import java.time.LocalDateTime;
import java.util.List;

public class Jornada_5 {
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

        Corredor corredor2 = new Corredor();
        corredor2.setCategoria(Categoria.AMADOR);
        corredor2.setIdade(19);
        corredor2.setNome("Sandiego");

        Corredor corredor3 = new Corredor();
        corredor3.setCategoria(Categoria.ELITE);
        corredor3.setIdade(27);
        corredor3.setNome("Fernando");


        corridaController.cadastrar(corrida);

        corredorController.cadastrar(corredor);
        corredorController.cadastrar(corredor2);
        corredorController.cadastrar(corredor3);

        corredorController.atualizarCorrida(1, corrida);
        corredorController.atualizarCorrida(2, corrida);
        corredorController.atualizarCorrida(3, corrida);

        corredorController.listarTodos().forEach(System.out::println);
        System.out.println("-----------------");

        LocalDateTime tempoCorrida1 = LocalDateTime.of(2026,8,22,22,5,10);
        LocalDateTime tempoCorrida2 = LocalDateTime.of(2026,8,22,20,5,10);
        LocalDateTime tempoCorrida3 = LocalDateTime.of(2026,8,22,18,5,10);

        corredorController.registarCorrida(tempoCorrida1, corrida, corredor);
        corredorController.registarCorrida(tempoCorrida2, corrida, corredor2);
        corredorController.registarCorrida(tempoCorrida3, corrida, corredor3);

        System.out.println("-----------------");
        corredorController.listarTodos().forEach(System.out::println);

        System.out.println("------------------------------------");

        corridaController.resultadoPorCategoria(corrida, List.of(corredor, corredor2, corredor3));

    }
}
