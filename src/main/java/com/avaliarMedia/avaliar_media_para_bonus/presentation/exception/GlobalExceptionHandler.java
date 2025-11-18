package com.avaliarMedia.avaliar_media_para_bonus.presentation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Manipulador global de exceções para a API.
 * Converte exceções em respostas HTTP apropriadas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata RuntimeException com mensagens de "não encontrado"
     * e retorna 404 Not Found.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        String message = ex.getMessage();
        
        // Se a mensagem contém "não encontrado" ou "não encontrada", retorna 404
        if (message != null && (message.toLowerCase().contains("não encontrado") || 
                                message.toLowerCase().contains("não encontrada"))) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Not Found");
            error.put("message", message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        
        // Outras RuntimeExceptions retornam 500
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal Server Error");
        error.put("message", message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Trata erros de validação (campos obrigatórios, formato inválido, etc.)
     * e retorna 400 Bad Request.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", "Validation Failed");
        
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            fieldErrors.put(error.getField(), error.getDefaultMessage())
        );
        
        errors.put("fields", fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}

