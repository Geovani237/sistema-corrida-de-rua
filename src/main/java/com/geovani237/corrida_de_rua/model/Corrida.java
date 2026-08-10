package com.geovani237.corrida_de_rua.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Corrida {

    private Integer id;
    private Double distancia;
    private LocalDate data;
    private String local;
}
