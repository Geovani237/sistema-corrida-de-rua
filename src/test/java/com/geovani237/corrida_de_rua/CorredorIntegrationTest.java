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
class CorredorIntegrationTest {

    @LocalServerPort
    private int port;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Test
    void cadastraCorredorVinculadoACorridaEConsultaNaListagem() throws Exception {
        long corridaId = criarCorrida();
        HttpResponse<String> corredorResponse = post(
                "/api/v1/corredores",
                """
                        {
                          "nome": "Ana Souza",
                          "idade": 28,
                          "categoria": "AMADOR",
                          "corridaId": %d
                        }
                        """.formatted(corridaId)
        );

        assertEquals(201, corredorResponse.statusCode());
        JsonNode corredorCriado = objectMapper.readTree(corredorResponse.body());
        long corredorId = corredorCriado.path("numeroPeito").asLong();
        assertTrue(corredorId > 0);
        assertEquals("Ana Souza", corredorCriado.path("nome").asText());
        assertEquals(corridaId, corredorCriado.path("corridaIds").get(0).asLong());

        HttpResponse<String> listagemResponse = get("/api/v1/corredores");

        assertEquals(200, listagemResponse.statusCode());
        JsonNode corredores = objectMapper.readTree(listagemResponse.body());
        JsonNode corredorListado = findById(corredores, corredorId);
        assertEquals("Ana Souza", corredorListado.path("nome").asText());
        assertEquals(corridaId, corredorListado.path("corridaIds").get(0).asLong());
    }

    private long criarCorrida() throws Exception {
        String body = """
                {
                  "distancia": 10.0,
                  "data": "%s",
                  "local": "Parque Central"
                }
                """.formatted(LocalDateTime.now().plusDays(1).withNano(0));
        HttpResponse<String> response = post("/api/v1/corridas", body);

        assertEquals(201, response.statusCode());
        long corridaId = objectMapper.readTree(response.body()).path("id").asLong();
        assertTrue(corridaId > 0);
        return corridaId;
    }

    private JsonNode findById(JsonNode corredores, long corredorId) {
        for (JsonNode corredor : corredores) {
            if (corredor.path("numeroPeito").asLong() == corredorId) {
                return corredor;
            }
        }
        throw new AssertionError("Corredor não encontrado na listagem: " + corredorId);
    }

    private HttpResponse<String> post(String path, String body) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(uri(path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> get(String path) throws Exception {
        return httpClient.send(
                HttpRequest.newBuilder(uri(path)).GET().build(),
                HttpResponse.BodyHandlers.ofString()
        );
    }

    private URI uri(String path) {
        return URI.create("http://localhost:" + port + path);
    }
}
