package com.geovani237.corrida_de_rua.service;

import com.geovani237.corrida_de_rua.dto.CorridaRequest;
import com.geovani237.corrida_de_rua.dto.CorridaResponse;
import com.geovani237.corrida_de_rua.entity.CorridaEntity;
import com.geovani237.corrida_de_rua.exception.ErroSistemicoException;
import com.geovani237.corrida_de_rua.mapper.CorridaMapper;
import com.geovani237.corrida_de_rua.repository.CorridaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CorridaServiceTest {

    @Mock
    private CorridaRepository corridaRepository;
    @Mock
    private CorridaMapper corridaMapper;
    @InjectMocks
    private CorridaService service;

    @Test
    void cadastraCorrida() {
        CorridaRequest request = new CorridaRequest(10.0, LocalDateTime.now(), "Parque");
        CorridaEntity entity = new CorridaEntity();
        CorridaResponse response = new CorridaResponse(1L, 10.0, request.data(), "Parque", List.of());
        when(corridaMapper.toEntity(request)).thenReturn(entity);
        when(corridaMapper.toResponse(entity)).thenReturn(response);

        assertEquals(response, service.cadastrar(request));
        verify(corridaRepository).save(entity);
    }

    @Test
    void converteFalhaAoCadastrarCorridaEmErroSistemico() {
        CorridaRequest request = new CorridaRequest(10.0, LocalDateTime.now(), "Parque");
        when(corridaMapper.toEntity(request)).thenThrow(new IllegalStateException("falha"));

        assertThrows(ErroSistemicoException.class, () -> service.cadastrar(request));
    }

    @Test
    void listaCorridas() {
        CorridaEntity entity = new CorridaEntity();
        CorridaResponse response = new CorridaResponse(1L, 5.0, LocalDateTime.now(), "Praça", List.of());
        when(corridaRepository.findAll()).thenReturn(List.of(entity));
        when(corridaMapper.toResponse(entity)).thenReturn(response);

        assertEquals(List.of(response), service.listarCorridas());
    }

    @Test
    void retornaListaVaziaQuandoNaoHaCorridas() {
        when(corridaRepository.findAll()).thenReturn(List.of());

        assertTrue(service.listarCorridas().isEmpty());
    }

    @Test
    void calculaResultadoPorCategoriaSemLancarExcecao() {
        assertDoesNotThrow(() -> service.resultadoPorCategoria(new CorridaEntity(), List.of()));
    }
}
