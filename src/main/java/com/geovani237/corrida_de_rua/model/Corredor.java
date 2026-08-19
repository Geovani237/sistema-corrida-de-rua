package com.geovani237.corrida_de_rua.model;

import lombok.Data;

import java.time.LocalTime;

@Data
public class Corredor {

    private Integer numeroPeito;
    private String nome;
    private Integer idade;
    private Categoria categoria;
    private Corrida corrida;
    private Boolean statusPagamento;
    private LocalTime duracaoCorrida;
}
