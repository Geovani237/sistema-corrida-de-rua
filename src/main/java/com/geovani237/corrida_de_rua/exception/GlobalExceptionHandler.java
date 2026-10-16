package com.geovani237.corrida_de_rua.exception;

import com.geovani237.corrida_de_rua.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> createErrorResponse(HttpStatus status, String message, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                java.time.Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {

        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    @ExceptionHandler(CorredorNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleCorredorNaoEncontradoException(CorredorNaoEncontradoException ex, WebRequest request) {

        return createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(CorridaNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleCorridaNaoEncontradoException(CorridaNaoEncontradoException ex, WebRequest request) {

        return createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(DadosInvalidosException.class)
    public ResponseEntity<ErrorResponse> handleDadosInvalidosException(DadosInvalidosException ex, WebRequest request) {

        return createErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<ErrorResponse> handleNegocioException(NegocioException ex, WebRequest request) {

        return createErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }
}
