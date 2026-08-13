package com.geovani237.corrida_de_rua.model;

import lombok.Data;

@Data
public class Corredor {

    private Integer numeroPeito;
    private String nome;
    private Integer idade;
    private String categoria;
    private Corrida corrida;
    private Boolean statusPagamento;
}
