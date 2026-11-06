package com.geovani237.corrida_de_rua.dto;

import com.geovani237.corrida_de_rua.enums.CategoriaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record CorredorRequest (

        @Schema(description = "${schema.corredor_request.nome}", example = "${example.corredor.nome}", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{validation.corredor.nome.obrigatorio}")
        String nome,

        @Schema(description = "${schema.corredor_request.idade}", example = "28", minimum = "12", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{validation.corredor.idade.obrigatoria}")
        @Min(value = 12, message = "{validation.corredor.idade.minima}")
        Integer idade,

        @Schema(description = "${schema.corredor_request.categoria}", example = "AMADOR", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{validation.corredor.categoria.obrigatoria}")
        CategoriaEnum categoria,

        @Schema(description = "${schema.corredor_request.corrida_id}", example = "1", minimum = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{validation.corredor.corrida_id.obrigatorio}")
        @Positive(message = "{validation.corredor.corrida_id.positivo}")
        Long corridaId
){


}
