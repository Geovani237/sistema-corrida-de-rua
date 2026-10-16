package com.geovani237.corrida_de_rua.controller;

import com.geovani237.corrida_de_rua.dto.CorredorRequest;
import com.geovani237.corrida_de_rua.dto.CorredorResponse;
import com.geovani237.corrida_de_rua.enums.CategoriaEnum;
import com.geovani237.corrida_de_rua.service.CorredorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CorredorControllerTest {

    @Mock
    private CorredorService service;
    @InjectMocks
    private CorredorController controller;

    private CorredorResponse response() {
        return new CorredorResponse(1L, "Ana", 28, CategoriaEnum.AMADOR, List.of(2L));
    }

    @Test
    void cadastraEDevolveCreated() {
        CorredorRequest request = new CorredorRequest("Ana", 28, CategoriaEnum.AMADOR, 2L);
        when(service.cadastrar(request)).thenReturn(response());

        var result = controller.cadastrar(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response(), result.getBody());
    }

    @Test
    void listaCorredoresComConteudo() {
        when(service.listarTodos()).thenReturn(List.of(response()));

        var result = controller.listarTodos();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(response()), result.getBody());
        verify(service, times(2)).listarTodos();
    }

    @Test
    void devolveNoContentQuandoNaoHaCorredores() {
        when(service.listarTodos()).thenReturn(List.of());

        assertEquals(HttpStatus.NO_CONTENT, controller.listarTodos().getStatusCode());
        verify(service).listarTodos();
    }

    @Test
    void atualizaCorridasDoCorredor() {
        when(service.atualizarCorrida(1L, 2L)).thenReturn(response());

        var result = controller.atualizarCorrida(1L, 2L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response(), result.getBody());
    }

    @Test
    void confirmaRetiradaDoKit() {
        assertEquals(HttpStatus.OK, controller.retirarKit(1L).getStatusCode());
        verify(service).retirarKit(1L);
    }

    @Test
    void confirmaRegistroDeChegada() {
        assertEquals(HttpStatus.OK, controller.registarCorrida(2L, 1L).getStatusCode());
        verify(service).registrarChegada(2L, 1L);
    }
}
