package com.geovani237.corrida_de_rua.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class Corrida {

    private Integer id;
    private Double distancia;
    private LocalDateTime data;
    private String local;
}
