package com.geovani237.corrida_de_rua.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "tb_corredores")
@Data
public class CorredorEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer idade;
//    private Categoria categoria;
//    private Corrida corrida;
    private Boolean statusPagamento;
    private LocalTime duracaoCorrida;
}
