package com.geovani237.corrida_de_rua.service;

import com.geovani237.corrida_de_rua.dto.CorredorRequest;
import com.geovani237.corrida_de_rua.dto.CorredorResponse;
import com.geovani237.corrida_de_rua.entity.CorredorEntity;
import com.geovani237.corrida_de_rua.entity.CorridaEntity;
import com.geovani237.corrida_de_rua.enums.CategoriaEnum;
import com.geovani237.corrida_de_rua.exception.CorredorNaoEncontradoException;
import com.geovani237.corrida_de_rua.exception.CorridaNaoEncontradoException;
import com.geovani237.corrida_de_rua.exception.ErroSistemicoException;
import com.geovani237.corrida_de_rua.mapper.CorredorMapper;
import com.geovani237.corrida_de_rua.repository.CorredorRepository;
import com.geovani237.corrida_de_rua.repository.CorridaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CorredorServiceTest {

    @Mock
    private CorredorRepository corredorRepository;
    @Mock
    private CorridaRepository corridaRepository;
    @Mock
    private CorredorMapper corredorMapper;
    @InjectMocks
    private CorredorService service;

    private CorredorEntity corredor;
    private CorridaEntity corrida;
    private CorredorResponse response;
    private CorredorRequest request;

    @BeforeEach
    void setUp() {
        corredor = new CorredorEntity();
        corredor.setId(1L);
        corredor.setNome("Ana");
        corredor.setIdade(28);
        corredor.setCategoria(CategoriaEnum.AMADOR);
        corrida = new CorridaEntity();
        corrida.setId(2L);
        corrida.setData(LocalDateTime.now().minusHours(1));
        response = new CorredorResponse(1L, "Ana", 28, CategoriaEnum.AMADOR, List.of(2L));
        request = new CorredorRequest("Ana", 28, CategoriaEnum.AMADOR, 2L);
    }

    @Test
    void cadastraCorredorVinculadoACorrida() {
        when(corredorMapper.toEntity(request)).thenReturn(corredor);
        when(corridaRepository.findById(2L)).thenReturn(Optional.of(corrida));
        when(corredorMapper.toResponse(corredor)).thenReturn(response);

        assertEquals(response, service.cadastrar(request));
        assertTrue(corredor.getCorridas().contains(corrida));
        verify(corredorRepository).save(corredor);
    }

    @Test
    void naoCadastraCorredorQuandoCorridaNaoExiste() {
        when(corredorMapper.toEntity(request)).thenReturn(corredor);
        when(corridaRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(CorridaNaoEncontradoException.class, () -> service.cadastrar(request));
        verify(corredorRepository, never()).save(any());
    }

    @Test
    void converteFalhaAoSalvarEmErroSistemico() {
        when(corredorMapper.toEntity(request)).thenReturn(corredor);
        when(corridaRepository.findById(2L)).thenReturn(Optional.of(corrida));
        when(corredorRepository.save(corredor)).thenThrow(new IllegalStateException("falha"));

        assertThrows(ErroSistemicoException.class, () -> service.cadastrar(request));
    }

    @Test
    void listaCorredores() {
        when(corredorRepository.findAll()).thenReturn(List.of(corredor));
        when(corredorMapper.toResponse(corredor)).thenReturn(response);

        assertEquals(List.of(response), service.listarTodos());
    }

    @Test
    void adicionaCorridaAoCorredor() {
        when(corredorRepository.findById(1L)).thenReturn(Optional.of(corredor));
        when(corridaRepository.findById(2L)).thenReturn(Optional.of(corrida));
        when(corredorMapper.toResponse(corredor)).thenReturn(response);

        assertEquals(response, service.atualizarCorrida(1L, 2L));
        assertTrue(corredor.getCorridas().contains(corrida));
        verify(corredorRepository).save(corredor);
    }

    @Test
    void rejeitaCorridaJaAssociada() {
        corredor.getCorridas().add(corrida);
        when(corredorRepository.findById(1L)).thenReturn(Optional.of(corredor));

        assertThrows(ErroSistemicoException.class, () -> service.atualizarCorrida(1L, 2L));
        verify(corridaRepository, never()).findById(any());
    }

    @Test
    void falhaAoAtualizarQuandoCorredorNaoExiste() {
        when(corredorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CorredorNaoEncontradoException.class, () -> service.atualizarCorrida(1L, 2L));
    }

    @Test
    void falhaAoAtualizarQuandoCorridaNaoExiste() {
        when(corredorRepository.findById(1L)).thenReturn(Optional.of(corredor));
        when(corridaRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(CorridaNaoEncontradoException.class, () -> service.atualizarCorrida(1L, 2L));
    }

    @Test
    void registraRetiradaDoKit() {
        when(corredorRepository.findById(1L)).thenReturn(Optional.of(corredor));

        service.retirarKit(1L);

        assertTrue(corredor.isStatusPagamento());
        verify(corredorRepository).save(corredor);
    }

    @Test
    void falhaAoRetirarKitDeCorredorInexistente() {
        when(corredorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CorredorNaoEncontradoException.class, () -> service.retirarKit(1L));
    }

    @Test
    void registraChegadaECalculaDuracao() {
        corredor.getCorridas().add(corrida);
        when(corredorRepository.findById(1L)).thenReturn(Optional.of(corredor));

        service.registrarChegada(2L, 1L);

        assertNotNull(corredor.getDuracaoCorrida());
        assertTrue(corredor.getDuracaoCorrida() >= 3600);
        verify(corredorRepository).save(corredor);
    }

    @Test
    void falhaAoRegistrarChegadaDeCorredorInexistente() {
        when(corredorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CorredorNaoEncontradoException.class, () -> service.registrarChegada(2L, 1L));
    }

    @Test
    void falhaAoRegistrarChegadaEmCorridaNaoAssociada() {
        when(corredorRepository.findById(1L)).thenReturn(Optional.of(corredor));

        assertThrows(CorridaNaoEncontradoException.class, () -> service.registrarChegada(2L, 1L));
    }
}
