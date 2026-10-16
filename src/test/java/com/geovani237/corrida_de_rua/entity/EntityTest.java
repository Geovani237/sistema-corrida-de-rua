package com.geovani237.corrida_de_rua.entity;

import com.geovani237.corrida_de_rua.enums.CategoriaEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @Test
    void inicializaDadosDeAuditoriaEPermiteAtualizaLos() {
        AuditDataEntity entity = new AuditDataEntity();

        assertEquals(LocalDate.now(), entity.getDataCriacao());
        assertTrue(entity.isStatus());
        entity.setId(1L);
        entity.setDataCriacao(LocalDate.of(2025, 1, 1));
        entity.setDataAtualizacao(LocalDate.of(2025, 1, 2));
        entity.setStatus(false);
        assertEquals(1L, entity.getId());
        assertEquals(LocalDate.of(2025, 1, 1), entity.getDataCriacao());
        assertEquals(LocalDate.of(2025, 1, 2), entity.getDataAtualizacao());
        assertFalse(entity.isStatus());
    }

    @Test
    void gettersESettersDoCorredorEntityFuncionam() {
        CorredorEntity entity = new CorredorEntity();
        entity.setNome("Ana");
        entity.setIdade(28);
        entity.setStatusPagamento(true);
        entity.setDuracaoCorrida(120.0);
        entity.setCategoria(CategoriaEnum.ELITE);
        entity.setCorridas(List.of(new CorridaEntity()));

        assertEquals("Ana", entity.getNome());
        assertEquals(28, entity.getIdade());
        assertTrue(entity.isStatusPagamento());
        assertEquals(120.0, entity.getDuracaoCorrida());
        assertEquals(CategoriaEnum.ELITE, entity.getCategoria());
        assertEquals(1, entity.getCorridas().size());
    }

    @Test
    void gettersESettersDoCorridaEntityFuncionam() {
        CorridaEntity entity = new CorridaEntity();
        LocalDateTime data = LocalDateTime.of(2026, 10, 16, 9, 0);
        entity.setDistancia(21.0);
        entity.setData(data);
        entity.setLocal("Centro");
        entity.setCorredores(List.of(new CorredorEntity()));

        assertEquals(21.0, entity.getDistancia());
        assertEquals(data, entity.getData());
        assertEquals("Centro", entity.getLocal());
        assertEquals(1, entity.getCorredores().size());
    }
}
