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
    @DisplayName("Deve retornar false para isValid quando valor é inválido")
    public void deveRetornarFalseParaIsValidQuandoValorInvalido() {
        // Arrange & Act & Assert
        assertFalse(new Media(-1.0).isValid());
        assertFalse(new Media(11.0).isValid());
        assertFalse(new Media(null).isValid());
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
    @DisplayName("Deve retornar false para isElegivelParaBonus quando valor <= 7.0 ou null")
    public void deveRetornarFalseParaIsElegivelParaBonusQuandoValorMenorOuIgual7() {
        // Arrange & Act & Assert
        assertFalse(new Media(7.0).isElegivelParaBonus());
        assertFalse(new Media(5.0).isElegivelParaBonus());
        assertFalse(new Media(null).isElegivelParaBonus());
    }
}
