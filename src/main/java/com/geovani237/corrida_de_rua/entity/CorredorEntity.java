package com.geovani237.corrida_de_rua.entity;

import com.geovani237.corrida_de_rua.enums.CategoriaEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_corredores")
@Data
public class CorredorEntity extends AuditDataEntity implements Serializable {

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private Integer idade;

    @Column(name = "status_pagamento", nullable = false)
    private boolean statusPagamento;

    @Column(name = "duracao_corrida")
    private Double duracaoCorrida;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaEnum categoria;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "tb_corredores_corridas",
        joinColumns = @JoinColumn(name = "corredor_id"),
        inverseJoinColumns = @JoinColumn(name = "corrida_id")
    )
    private List<CorridaEntity> corridas = new ArrayList<>();

}
