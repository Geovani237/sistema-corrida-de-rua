package com.geovani237.corrida_de_rua.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class OpenApiMessageCustomizer {

    private final MessageSource messageSource;

    public OpenApiMessageCustomizer(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Bean
    OpenApiCustomizer localizeOpenApiDescriptions() {
        return openApi -> {
            localizeTags(openApi);
            localizeOperations(openApi);
            localizeSchemas(openApi);
        };
    }

    private void localizeTags(OpenAPI openApi) {
        Map<String, TagMessages> tags = Map.of(
                "tag.corredor", new TagMessages("api.tag.corredor.name", "api.tag.corredor.description"),
                "tag.corrida", new TagMessages("api.tag.corrida.name", "api.tag.corrida.description")
        );
        if (openApi.getTags() != null) {
            for (Tag tag : openApi.getTags()) {
                TagMessages messages = tags.get(tag.getName());
                if (messages != null) {
                    tag.setName(message(messages.name()));
                    tag.setDescription(message(messages.description()));
                }
            }
        }

        if (openApi.getPaths() != null) {
            openApi.getPaths().values().stream()
                    .flatMap(path -> path.readOperations().stream())
                    .filter(operation -> operation.getTags() != null)
                    .forEach(operation -> operation.setTags(operation.getTags().stream()
                            .map(tag -> tags.containsKey(tag) ? message(tags.get(tag).name()) : tag)
                            .toList()));
        }
    }

    private void localizeOperations(OpenAPI openApi) {
        if (openApi.getPaths() == null) {
            return;
        }

        openApi.getPaths().values().stream()
                .flatMap(path -> path.readOperations().stream())
                .forEach(this::localizeOperation);
    }

    private void localizeSchemas(OpenAPI openApi) {
        if (openApi.getComponents() == null || openApi.getComponents().getSchemas() == null) {
            return;
        }

        openApi.getComponents().getSchemas().values().forEach(this::localizeSchema);
    }

    private void localizeSchema(Schema<?> schema) {
        if (schema.getDescription() != null) {
            schema.setDescription(resolvePlaceholder(schema.getDescription()));
        }
        if (schema.getExample() instanceof String example) {
            schema.setExample(resolvePlaceholder(example));
        }
        if (schema.getProperties() != null) {
            schema.getProperties().values().forEach(property -> localizeSchema((Schema<?>) property));
        }
        if (schema.getItems() != null) {
            localizeSchema(schema.getItems());
        }
    }

    private void localizeOperation(Operation operation) {
        String operationId = operation.getOperationId();
        if (operationId == null) {
            return;
        }

        OperationMessages messages = operationMessages(operationId);
        if (messages == null) {
            return;
        }

        operation.setSummary(message(messages.summary()));
        operation.setDescription(message(messages.description()));
        operation.getResponses().forEach((code, response) -> {
            String key = messages.responses().get(code);
            if (key != null) {
                response.setDescription(message(key));
            }
        });

        if (operation.getParameters() != null) {
            operation.getParameters().forEach(parameter -> {
                String key = messages.parameters().get(parameter.getName());
                if (key != null) {
                    parameter.setDescription(message(key));
                }
            });
        }

        if (operation.getRequestBody() != null && messages.requestBody() != null) {
            operation.getRequestBody().setDescription(message(messages.requestBody()));
        }
    }

    private OperationMessages operationMessages(String operationId) {
        return switch (operationId) {
            case "corredorCadastrar" -> new OperationMessages(
                    "api.corredor.cadastrar.summary",
                    "api.corredor.cadastrar.description",
                    Map.of(
                            "201", "api.corredor.cadastrar.response.201",
                            "400", "api.corredor.cadastrar.response.400",
                            "404", "api.corredor.cadastrar.response.404",
                            "500", "api.corredor.cadastrar.response.500"
                    ),
                    Map.of(),
                    "api.corredor.cadastrar.request"
            );
            case "corredorListarTodos" -> new OperationMessages(
                    "api.corredor.listar.summary",
                    "api.corredor.listar.description",
                    Map.of(
                            "200", "api.corredor.listar.response.200",
                            "204", "api.corredor.listar.response.204",
                            "500", "api.corredor.listar.response.500"
                    ),
                    Map.of(),
                    null
            );
            case "corredorAtualizarCorrida" -> new OperationMessages(
                    "api.corredor.atualizar_corrida.summary",
                    "api.corredor.atualizar_corrida.description",
                    Map.of(
                            "200", "api.corredor.atualizar_corrida.response.200",
                            "404", "api.corredor.atualizar_corrida.response.404",
                            "500", "api.corredor.atualizar_corrida.response.500"
                    ),
                    Map.of(
                            "numeroPeito", "api.corredor.atualizar_corrida.parameter.numeroPeito",
                            "corridaId", "api.corredor.atualizar_corrida.parameter.corridaId"
                    ),
                    null
            );
            case "corredorRetirarKit" -> new OperationMessages(
                    "api.corredor.retirar_kit.summary",
                    "api.corredor.retirar_kit.description",
                    Map.of(
                            "200", "api.corredor.retirar_kit.response.200",
                            "404", "api.corredor.retirar_kit.response.404",
                            "500", "api.corredor.retirar_kit.response.500"
                    ),
                    Map.of("corredorId", "api.corredor.retirar_kit.parameter.corredorId"),
                    null
            );
            case "corredorRegistrarChegada" -> new OperationMessages(
                    "api.corredor.registrar_chegada.summary",
                    "api.corredor.registrar_chegada.description",
                    Map.of(
                            "200", "api.corredor.registrar_chegada.response.200",
                            "404", "api.corredor.registrar_chegada.response.404",
                            "500", "api.corredor.registrar_chegada.response.500"
                    ),
                    Map.of(
                            "corridaId", "api.corredor.registrar_chegada.parameter.corridaId",
                            "corredorId", "api.corredor.registrar_chegada.parameter.corredorId"
                    ),
                    null
            );
            case "corridaCadastrar" -> new OperationMessages(
                    "api.corrida.cadastrar.summary",
                    "api.corrida.cadastrar.description",
                    Map.of(
                            "201", "api.corrida.cadastrar.response.201",
                            "400", "api.corrida.cadastrar.response.400",
                            "500", "api.corrida.cadastrar.response.500"
                    ),
                    Map.of(),
                    "api.corrida.cadastrar.request"
            );
            case "corridaListarCorridas" -> new OperationMessages(
                    "api.corrida.listar.summary",
                    "api.corrida.listar.description",
                    Map.of(
                            "200", "api.corrida.listar.response.200",
                            "204", "api.corrida.listar.response.204",
                            "500", "api.corrida.listar.response.500"
                    ),
                    Map.of(),
                    null
            );
            case "corridaResultadoPorCategoria" -> new OperationMessages(
                    "api.corrida.resultado.summary",
                    "api.corrida.resultado.description",
                    Map.of(
                            "200", "api.corrida.resultado.response.200",
                            "404", "api.corrida.resultado.response.404"
                    ),
                    Map.of("corridaId", "api.corrida.resultado.parameter.corridaId"),
                    null
            );
            default -> null;
        };
    }

    private String message(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    private String resolvePlaceholder(String value) {
        if (value.startsWith("${") && value.endsWith("}")) {
            String key = value.substring(2, value.length() - 1);
            return messageSource.getMessage(key, null, value, LocaleContextHolder.getLocale());
        }
        return value;
    }

    private record OperationMessages(
            String summary,
            String description,
            Map<String, String> responses,
            Map<String, String> parameters,
            String requestBody
    ) {
    }

    private record TagMessages(String name, String description) {
    }
}
