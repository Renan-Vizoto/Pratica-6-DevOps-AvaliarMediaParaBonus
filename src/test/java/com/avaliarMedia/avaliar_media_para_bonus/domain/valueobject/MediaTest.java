package com.avaliarMedia.avaliar_media_para_bonus.domain.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o Value Object Media.
 */
@DisplayName("Testes Unitários - Media Value Object")
public class MediaTest {

    @Test
    @DisplayName("Deve criar Media com valor válido")
    public void deveCriarMediaComValorValido() {
        // Arrange & Act
        Media media = new Media(8.5);

        // Assert
        assertNotNull(media);
        assertEquals(8.5, media.getValor());
    }

    @Test
    @DisplayName("Deve retornar true para isValid quando valor está entre 0.0 e 10.0")
    public void deveRetornarTrueParaIsValidQuandoValorValido() {
        // Arrange & Act & Assert
        assertTrue(new Media(0.0).isValid());
        assertTrue(new Media(5.0).isValid());
        assertTrue(new Media(7.0).isValid());
        assertTrue(new Media(10.0).isValid());
    }

    @Test
    @DisplayName("Deve retornar false para isValid quando valor é negativo")
    public void deveRetornarFalseParaIsValidQuandoValorNegativo() {
        // Arrange
        Media media = new Media(-1.0);

        // Act & Assert
        assertFalse(media.isValid());
    }

    @Test
    @DisplayName("Deve retornar false para isValid quando valor é maior que 10.0")
    public void deveRetornarFalseParaIsValidQuandoValorMaiorQue10() {
        // Arrange
        Media media = new Media(11.0);

        // Act & Assert
        assertFalse(media.isValid());
    }

    @Test
    @DisplayName("Deve retornar false para isValid quando valor é null")
    public void deveRetornarFalseParaIsValidQuandoValorNull() {
        // Arrange
        Media media = new Media(null);

        // Act & Assert
        assertFalse(media.isValid());
    }

    @Test
    @DisplayName("Deve retornar true para isElegivelParaBonus quando valor maior que 7.0")
    public void deveRetornarTrueParaIsElegivelParaBonusQuandoValorMaiorQue7() {
        // Arrange & Act & Assert
        assertTrue(new Media(7.1).isElegivelParaBonus());
        assertTrue(new Media(8.0).isElegivelParaBonus());
        assertTrue(new Media(9.5).isElegivelParaBonus());
        assertTrue(new Media(10.0).isElegivelParaBonus());
    }

    @Test
    @DisplayName("Deve retornar false para isElegivelParaBonus quando valor igual a 7.0")
    public void deveRetornarFalseParaIsElegivelParaBonusQuandoValorIgual7() {
        // Arrange
        Media media = new Media(7.0);

        // Act & Assert
        assertFalse(media.isElegivelParaBonus());
    }

    @Test
    @DisplayName("Deve retornar false para isElegivelParaBonus quando valor menor que 7.0")
    public void deveRetornarFalseParaIsElegivelParaBonusQuandoValorMenorQue7() {
        // Arrange & Act & Assert
        assertFalse(new Media(0.0).isElegivelParaBonus());
        assertFalse(new Media(5.0).isElegivelParaBonus());
        assertFalse(new Media(6.9).isElegivelParaBonus());
    }

    @Test
    @DisplayName("Deve retornar false para isElegivelParaBonus quando valor é null")
    public void deveRetornarFalseParaIsElegivelParaBonusQuandoValorNull() {
        // Arrange
        Media media = new Media(null);

        // Act & Assert
        assertFalse(media.isElegivelParaBonus());
    }

    @Test
    @DisplayName("Deve implementar equals corretamente")
    public void deveImplementarEqualsCorretamente() {
        // Arrange
        Media media1 = new Media(8.5);
        Media media2 = new Media(8.5);
        Media media3 = new Media(7.0);

        // Act & Assert
        assertEquals(media1, media2);
        assertNotEquals(media1, media3);
    }

    @Test
    @DisplayName("Deve implementar hashCode corretamente")
    public void deveImplementarHashCodeCorretamente() {
        // Arrange
        Media media1 = new Media(8.5);
        Media media2 = new Media(8.5);

        // Act & Assert
        assertEquals(media1.hashCode(), media2.hashCode());
    }

    @Test
    @DisplayName("Deve implementar toString corretamente")
    public void deveImplementarToStringCorretamente() {
        // Arrange
        Media media = new Media(8.5);

        // Act
        String toString = media.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("8.5"));
    }
}

