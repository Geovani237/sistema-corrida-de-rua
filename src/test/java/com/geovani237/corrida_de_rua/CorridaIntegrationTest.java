package com.geovani237.corrida_de_rua;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CorridaIntegrationTest {

    @LocalServerPort
    private int port;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Test
    void cadastraCorridaEConsultaNaListagem() throws Exception {
        LocalDateTime dataCorrida = LocalDateTime.now().plusDays(1).withNano(0);
        HttpResponse<String> response = post(
                """
                        {
                          "distancia": 10.0,
                          "data": "%s",
                          "local": "Parque Central"
                        }
                        """.formatted(dataCorrida)
        );

        assertEquals(201, response.statusCode());
        JsonNode corridaCriada = objectMapper.readTree(response.body());
        long corridaId = corridaCriada.path("id").asLong();
        assertTrue(corridaId > 0);
        assertEquals(10.0, corridaCriada.path("distancia").asDouble());
        assertEquals("Parque Central", corridaCriada.path("local").asText());

        HttpResponse<String> listagemResponse = get();

        assertEquals(200, listagemResponse.statusCode());
        JsonNode corridas = objectMapper.readTree(listagemResponse.body());
        JsonNode corridaListada = findById(corridas, corridaId);
        assertEquals(10.0, corridaListada.path("distancia").asDouble());
        assertEquals("Parque Central", corridaListada.path("local").asText());
    }

    private JsonNode findById(JsonNode corridas, long corridaId) {
        for (JsonNode corrida : corridas) {
            if (corrida.path("id").asLong() == corridaId) {
                return corrida;
            }
        }
        throw new AssertionError("Corrida não encontrada na listagem: " + corridaId);
    }

    private HttpResponse<String> post(String body) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(uri())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> get() throws Exception {
        return httpClient.send(
                HttpRequest.newBuilder(uri()).GET().build(),
                HttpResponse.BodyHandlers.ofString()
        );
    }

    private URI uri() {
        return URI.create("http://localhost:" + port + "/api/v1/corridas");
    }
}
