package com.avaliarMedia.avaliar_media_para_bonus.domain.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o Value Object Nota.
 */
@DisplayName("Testes Unitários - Nota Value Object")
public class NotaTest {

    @Test
    @DisplayName("Deve criar Nota com valor válido")
    public void deveCriarNotaComValorValido() {
        // Arrange & Act
        Nota nota = new Nota(8.5);

        // Assert
        assertNotNull(nota);
        assertEquals(8.5, nota.getValor());
    }

    @Test
    @DisplayName("Deve retornar true para isValid quando valor está entre 0.0 e 10.0")
    public void deveRetornarTrueParaIsValidQuandoValorValido() {
        // Arrange & Act & Assert
        assertTrue(new Nota(0.0).isValid());
        assertTrue(new Nota(5.0).isValid());
        assertTrue(new Nota(7.0).isValid());
        assertTrue(new Nota(10.0).isValid());
    }

    @Test
    @DisplayName("Deve retornar false para isValid quando valor é inválido")
    public void deveRetornarFalseParaIsValidQuandoValorInvalido() {
        // Arrange & Act & Assert
        assertFalse(new Nota(-1.0).isValid());
        assertFalse(new Nota(11.0).isValid());
        assertFalse(new Nota(null).isValid());
    }
}
