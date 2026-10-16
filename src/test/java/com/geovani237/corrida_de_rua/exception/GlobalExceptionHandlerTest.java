package com.geovani237.corrida_de_rua.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();
    private final ServletWebRequest request = new ServletWebRequest(new MockHttpServletRequest("GET", "/api/test"));

    @Test
    void trataErroInterno() {
        assertErro(handler.handleException(new Exception("erro"), request), HttpStatus.INTERNAL_SERVER_ERROR, "erro");
    }

    @Test
    void trataCorredorNaoEncontrado() {
        assertErro(handler.handleCorredorNaoEncontradoException(
                new CorredorNaoEncontradoException("corredor"), request), HttpStatus.NOT_FOUND, "corredor");
    }

    @Test
    void trataCorridaNaoEncontrada() {
        assertErro(handler.handleCorridaNaoEncontradoException(
                new CorridaNaoEncontradoException("corrida"), request), HttpStatus.NOT_FOUND, "corrida");
    }

    @Test
    void trataDadosInvalidos() {
        assertErro(handler.handleDadosInvalidosException(
                new DadosInvalidosException("dados"), request), HttpStatus.BAD_REQUEST, "dados");
    }

    @Test
    void trataErroDeNegocio() {
        assertErro(handler.handleNegocioException(new NegocioException("negocio"), request),
                HttpStatus.BAD_REQUEST, "negocio");
    }

    private void assertErro(org.springframework.http.ResponseEntity<com.geovani237.corrida_de_rua.dto.ErrorResponse> response,
                            HttpStatus status, String message) {
        assertEquals(status, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(status.value(), response.getBody().status());
        assertEquals(status.getReasonPhrase(), response.getBody().error());
        assertEquals(message, response.getBody().message());
        assertEquals("/api/test", response.getBody().path());
        assertNotNull(response.getBody().timestamp());
    }
}
