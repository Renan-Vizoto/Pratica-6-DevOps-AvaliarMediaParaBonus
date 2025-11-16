package com.avaliarMedia.avaliar_media_para_bonus.presentation.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para GlobalExceptionHandler.
 */
@DisplayName("Testes Unitários - GlobalExceptionHandler")
public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    public void setup() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Deve retornar 404 quando RuntimeException contém 'não encontrado'")
    public void deveRetornar404QuandoRuntimeExceptionContemNaoEncontrado() {
        // Arrange
        RuntimeException exception = new RuntimeException("Aluno não encontrado com ID: 123");

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleRuntimeException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Not Found", response.getBody().get("error"));
        assertEquals("Aluno não encontrado com ID: 123", response.getBody().get("message"));
    }

    @Test
    @DisplayName("Deve retornar 404 quando RuntimeException contém 'não encontrada'")
    public void deveRetornar404QuandoRuntimeExceptionContemNaoEncontrada() {
        // Arrange
        RuntimeException exception = new RuntimeException("Avaliação não encontrada para aluno ID: 456");

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleRuntimeException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Not Found", response.getBody().get("error"));
        assertEquals("Avaliação não encontrada para aluno ID: 456", response.getBody().get("message"));
    }

    @Test
    @DisplayName("Deve retornar 404 quando RuntimeException contém 'NÃO ENCONTRADO' (maiúsculas)")
    public void deveRetornar404QuandoRuntimeExceptionContemNaoEncontradoMaiusculas() {
        // Arrange
        RuntimeException exception = new RuntimeException("REGISTRO NÃO ENCONTRADO");

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleRuntimeException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Not Found", response.getBody().get("error"));
    }

    @Test
    @DisplayName("Deve retornar 500 quando RuntimeException não contém 'não encontrado'")
    public void deveRetornar500QuandoRuntimeExceptionNaoContemNaoEncontrado() {
        // Arrange
        RuntimeException exception = new RuntimeException("Erro ao processar dados");

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleRuntimeException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Internal Server Error", response.getBody().get("error"));
        assertEquals("Erro ao processar dados", response.getBody().get("message"));
    }

    @Test
    @DisplayName("Deve retornar 500 quando RuntimeException tem mensagem null")
    public void deveRetornar500QuandoRuntimeExceptionTemMensagemNull() {
        // Arrange
        RuntimeException exception = new RuntimeException((String) null);

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleRuntimeException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Internal Server Error", response.getBody().get("error"));
    }

    @Test
    @DisplayName("Deve retornar 400 quando MethodArgumentNotValidException ocorre")
    public void deveRetornar400QuandoMethodArgumentNotValidException() {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("alunoDTO", "nome", "Nome é obrigatório");
        FieldError fieldError2 = new FieldError("alunoDTO", "email", "Email deve ter formato válido");
        
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));
        
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleValidationException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Validation Failed", response.getBody().get("error"));
        
        @SuppressWarnings("unchecked")
        Map<String, String> fieldErrors = (Map<String, String>) response.getBody().get("fields");
        assertNotNull(fieldErrors);
        assertEquals("Nome é obrigatório", fieldErrors.get("nome"));
        assertEquals("Email deve ter formato válido", fieldErrors.get("email"));
    }

    @Test
    @DisplayName("Deve retornar 400 quando MethodArgumentNotValidException sem erros de campo")
    public void deveRetornar400QuandoMethodArgumentNotValidExceptionSemErros() {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of());
        
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleValidationException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Validation Failed", response.getBody().get("error"));
        
        @SuppressWarnings("unchecked")
        Map<String, String> fieldErrors = (Map<String, String>) response.getBody().get("fields");
        assertNotNull(fieldErrors);
        assertTrue(fieldErrors.isEmpty());
    }

    @Test
    @DisplayName("Deve tratar RuntimeException com mensagem vazia")
    public void deveTratarRuntimeExceptionComMensagemVazia() {
        // Arrange
        RuntimeException exception = new RuntimeException("");

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleRuntimeException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Deve ser case-insensitive para detecção de 'não encontrado'")
    public void deveSerCaseInsensitiveParaDeteccaoNaoEncontrado() {
        // Arrange & Act & Assert
        assertEquals(HttpStatus.NOT_FOUND, 
            exceptionHandler.handleRuntimeException(new RuntimeException("não encontrado")).getStatusCode());
        
        assertEquals(HttpStatus.NOT_FOUND, 
            exceptionHandler.handleRuntimeException(new RuntimeException("NÃO ENCONTRADO")).getStatusCode());
        
        assertEquals(HttpStatus.NOT_FOUND, 
            exceptionHandler.handleRuntimeException(new RuntimeException("Não Encontrado")).getStatusCode());
        
        assertEquals(HttpStatus.NOT_FOUND, 
            exceptionHandler.handleRuntimeException(new RuntimeException("Item não encontrada")).getStatusCode());
    }
}

