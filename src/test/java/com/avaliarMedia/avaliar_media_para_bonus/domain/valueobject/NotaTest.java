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
    @DisplayName("Deve retornar false para isValid quando valor é negativo")
    public void deveRetornarFalseParaIsValidQuandoValorNegativo() {
        // Arrange
        Nota nota = new Nota(-1.0);

        // Act & Assert
        assertFalse(nota.isValid());
    }

    @Test
    @DisplayName("Deve retornar false para isValid quando valor é maior que 10.0")
    public void deveRetornarFalseParaIsValidQuandoValorMaiorQue10() {
        // Arrange
        Nota nota = new Nota(11.0);

        // Act & Assert
        assertFalse(nota.isValid());
    }

    @Test
    @DisplayName("Deve retornar false para isValid quando valor é null")
    public void deveRetornarFalseParaIsValidQuandoValorNull() {
        // Arrange
        Nota nota = new Nota(null);

        // Act & Assert
        assertFalse(nota.isValid());
    }

    @Test
    @DisplayName("Deve implementar equals corretamente")
    public void deveImplementarEqualsCorretamente() {
        // Arrange
        Nota nota1 = new Nota(8.5);
        Nota nota2 = new Nota(8.5);
        Nota nota3 = new Nota(7.0);

        // Act & Assert
        assertEquals(nota1, nota2);
        assertNotEquals(nota1, nota3);
    }

    @Test
    @DisplayName("Deve implementar hashCode corretamente")
    public void deveImplementarHashCodeCorretamente() {
        // Arrange
        Nota nota1 = new Nota(8.5);
        Nota nota2 = new Nota(8.5);

        // Act & Assert
        assertEquals(nota1.hashCode(), nota2.hashCode());
    }

    @Test
    @DisplayName("Deve implementar toString corretamente")
    public void deveImplementarToStringCorretamente() {
        // Arrange
        Nota nota = new Nota(8.5);

        // Act
        String toString = nota.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("8.5"));
    }
}

