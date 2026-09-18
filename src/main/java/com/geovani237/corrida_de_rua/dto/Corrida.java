package com.geovani237.corrida_de_rua.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Corrida {

    private Integer id;
    private Double distancia;
    private LocalDateTime data;
    private String local;
}
