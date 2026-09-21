package com.geovani237.corrida_de_rua.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_corridas")
@Data
public class CorridaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double distancia;
    private LocalDateTime data;
    private String local;
}

