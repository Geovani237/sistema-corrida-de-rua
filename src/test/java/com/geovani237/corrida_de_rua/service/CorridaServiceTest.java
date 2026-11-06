package com.geovani237.corrida_de_rua.service;

import com.geovani237.corrida_de_rua.dto.CorridaRequest;
import com.geovani237.corrida_de_rua.dto.CorridaResponse;
import com.geovani237.corrida_de_rua.dto.CorredorResultadoResponse;
import com.geovani237.corrida_de_rua.dto.ResultadoPorCategoriaResponse;
import com.geovani237.corrida_de_rua.entity.CorredorEntity;
import com.geovani237.corrida_de_rua.entity.CorridaEntity;
import com.geovani237.corrida_de_rua.enums.CategoriaEnum;
import com.geovani237.corrida_de_rua.exception.CorridaNaoEncontradoException;
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
    void retornaResultadoOrdenadoPorTempoESeparadoPorCategoria() {
        CorredorEntity amadorMaisRapido = corredor(1L, "Ana", CategoriaEnum.AMADOR, 1200.0);
        CorredorEntity amadorMaisLento = corredor(2L, "Bia", CategoriaEnum.AMADOR, 1500.0);
        CorredorEntity elite = corredor(3L, "Caio", CategoriaEnum.ELITE, 1100.0);
        CorredorEntity semTempo = corredor(4L, "Duda", CategoriaEnum.ELITE, null);
        CorridaEntity corrida = new CorridaEntity();
        corrida.setCorredores(List.of(amadorMaisLento, elite, semTempo, amadorMaisRapido));
        when(corridaRepository.findById(10L)).thenReturn(java.util.Optional.of(corrida));

        ResultadoPorCategoriaResponse resultado = service.resultadoPorCategoria(10L);

        assertEquals(List.of(
                new CorredorResultadoResponse(1L, "Ana", 1200.0),
                new CorredorResultadoResponse(2L, "Bia", 1500.0)
        ), resultado.amadores());
        assertEquals(List.of(
                new CorredorResultadoResponse(3L, "Caio", 1100.0)
        ), resultado.elite());
    }

    @Test
    void retornaCategoriasVaziasQuandoCorridaNaoTemCorredores() {
        CorridaEntity corrida = new CorridaEntity();
        corrida.setCorredores(List.of());
        when(corridaRepository.findById(10L)).thenReturn(java.util.Optional.of(corrida));

        assertEquals(new ResultadoPorCategoriaResponse(List.of(), List.of()), service.resultadoPorCategoria(10L));
    }

    @Test
    void lancaExcecaoQuandoCorridaNaoExiste() {
        when(corridaRepository.findById(10L)).thenReturn(java.util.Optional.empty());

        assertThrows(CorridaNaoEncontradoException.class, () -> service.resultadoPorCategoria(10L));
    }

    private CorredorEntity corredor(Long id, String nome, CategoriaEnum categoria, Double duracao) {
        CorredorEntity corredor = new CorredorEntity();
        corredor.setId(id);
        corredor.setNome(nome);
        corredor.setCategoria(categoria);
        corredor.setDuracaoCorrida(duracao);
        return corredor;
    }
}
