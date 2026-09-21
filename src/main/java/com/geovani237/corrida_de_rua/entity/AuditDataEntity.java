package com.geovani237.corrida_de_rua.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@MappedSuperclass
@Data
public class AuditDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataCriacao;

    private LocalDate dataAtualizacao;

    private boolean status;

    public AuditDataEntity() {
        this.dataCriacao = LocalDate.now();
//        this.dataAtualizacao = LocalDate.now();
        this.status = true;
    }
}
