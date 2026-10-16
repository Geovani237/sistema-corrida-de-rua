package com.geovani237.corrida_de_rua.mapper;

import com.geovani237.corrida_de_rua.dto.CorredorRequest;
import com.geovani237.corrida_de_rua.dto.CorridaRequest;
import com.geovani237.corrida_de_rua.entity.CorredorEntity;
import com.geovani237.corrida_de_rua.entity.CorridaEntity;
import com.geovani237.corrida_de_rua.enums.CategoriaEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

    private final CorredorMapper corredorMapper = new CorredorMapperImpl();
    private final CorridaMapper corridaMapper = new CorridaMapperImpl();

    @Test
    void converteRequestDeCorredorParaEntidade() {
        CorredorRequest request = new CorredorRequest("Ana", 28, CategoriaEnum.ELITE, 7L);

        CorredorEntity result = corredorMapper.toEntity(request);

        assertEquals("Ana", result.getNome());
        assertEquals(28, result.getIdade());
        assertEquals(CategoriaEnum.ELITE, result.getCategoria());
    }

    @Test
    void converteEntidadeDeCorredorParaResponse() {
        CorredorEntity entity = new CorredorEntity();
        entity.setId(4L);
        entity.setNome("Bia");
        entity.setIdade(31);
        entity.setCategoria(CategoriaEnum.AMADOR);
        CorridaEntity corrida = new CorridaEntity();
        corrida.setId(9L);
        entity.setCorridas(List.of(corrida));

        var result = corredorMapper.toResponse(entity);

        assertEquals(4L, result.numeroPeito());
        assertEquals(List.of(9L), result.corridaIds());
    }

    @Test
    void converteRequestDeCorridaParaEntidade() {
        LocalDateTime data = LocalDateTime.of(2026, 10, 16, 8, 0);
        CorridaEntity result = corridaMapper.toEntity(new CorridaRequest(10.0, data, "Parque"));

        assertEquals(10.0, result.getDistancia());
        assertEquals(data, result.getData());
        assertEquals("Parque", result.getLocal());
    }

    @Test
    void converteEntidadeDeCorridaParaResponseComIdsDosCorredores() {
        CorridaEntity entity = new CorridaEntity();
        entity.setId(3L);
        entity.setDistancia(5.0);
        entity.setData(LocalDateTime.now());
        entity.setLocal("Praça");
        CorredorEntity corredor = new CorredorEntity();
        corredor.setId(8L);
        entity.setCorredores(List.of(corredor));

        var result = corridaMapper.toResponse(entity);

        assertEquals(List.of(8L), result.corredores());
    }

    @Test
    void retornaNuloQuandoListaDeCorredoresDaCorridaEhNula() {
        assertNull(corridaMapper.mapCorredoresToIds(null));
    }
}
