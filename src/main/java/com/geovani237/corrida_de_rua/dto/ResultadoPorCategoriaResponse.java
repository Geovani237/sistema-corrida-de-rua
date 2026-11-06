package com.geovani237.corrida_de_rua.dto;

import java.util.List;

public record ResultadoPorCategoriaResponse(
        List<CorredorResultadoResponse> amadores,
        List<CorredorResultadoResponse> elite
) {
}
