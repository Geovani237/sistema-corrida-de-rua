package com.geovani237.corrida_de_rua.controller;

import com.geovani237.corrida_de_rua.dto.CorridaRequest;
import com.geovani237.corrida_de_rua.dto.CorridaResponse;
import com.geovani237.corrida_de_rua.entity.CorridaEntity;
import com.geovani237.corrida_de_rua.service.CorridaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CorridaControllerTest {

    @Mock
    private CorridaService service;
    @InjectMocks
    private CorridaController controller;

    @Test
    void cadastraCorridaEDevolveCreated() {
        CorridaRequest request = new CorridaRequest(10.0, LocalDateTime.now(), "Parque");
        CorridaResponse response = new CorridaResponse(1L, 10.0, request.data(), "Parque", List.of());
        when(service.cadastrar(request)).thenReturn(response);

        var result = controller.cadastrar(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void listaCorridasComConteudo() {
        CorridaResponse response = new CorridaResponse(1L, 5.0, LocalDateTime.now(), "Praça", List.of());
        when(service.listarCorridas()).thenReturn(List.of(response));

        var result = controller.listarCorridas();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(response), result.getBody());
        verify(service, times(2)).listarCorridas();
    }

    @Test
    void devolveNoContentQuandoNaoHaCorridas() {
        when(service.listarCorridas()).thenReturn(List.of());

        assertEquals(HttpStatus.NO_CONTENT, controller.listarCorridas().getStatusCode());
        verify(service).listarCorridas();
    }

    @Test
    void encaminhaConsultaDeResultadoPorCategoria() {
        CorridaEntity corrida = new CorridaEntity();
        controller.resultadoPorCategoria(corrida, List.of());

        verify(service).resultadoPorCategoria(corrida, List.of());
    }
}
