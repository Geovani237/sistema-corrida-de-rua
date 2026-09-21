package com.geovani237.corrida_de_rua.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_corridas")
@Data
public class CorridaEntity extends AuditDataEntity implements Serializable {

    @Column(nullable = false)
    private Double distancia;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(nullable = false, length = 200)
    private String local;

    @ManyToMany(mappedBy = "corridas", fetch = FetchType.LAZY)
    private List<CorredorEntity> corredores;
}

