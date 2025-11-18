package com.avaliarMedia.avaliar_media_para_bonus.domain.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o Value Object QuantidadeCursos.
 */
@DisplayName("Testes Unitários - QuantidadeCursos Value Object")
public class QuantidadeCursosTest {

    @Test
    @DisplayName("Deve criar QuantidadeCursos com valor válido")
    public void deveCriarQuantidadeCursosComValorValido() {
        // Arrange & Act
        QuantidadeCursos quantidade = new QuantidadeCursos(3);

        // Assert
        assertNotNull(quantidade);
        assertEquals(3, quantidade.getQuantidade());
    }

    @Test
    @DisplayName("Deve calcular 3 cursos bônus para nota maior que 7.0")
    public void deveCalcular3CursosBonusParaNotaMaiorQue7() {
        // Arrange & Act & Assert
        assertEquals(3, QuantidadeCursos.calcularPorNota(7.1).getQuantidade());
        assertEquals(3, QuantidadeCursos.calcularPorNota(8.0).getQuantidade());
        assertEquals(3, QuantidadeCursos.calcularPorNota(10.0).getQuantidade());
    }

    @Test
    @DisplayName("Deve calcular 0 cursos bônus para nota <= 7.0 ou null")
    public void deveCalcular0CursosBonusParaNotaMenorOuIgual7() {
        // Arrange & Act & Assert
        assertEquals(0, QuantidadeCursos.calcularPorNota(7.0).getQuantidade());
        assertEquals(0, QuantidadeCursos.calcularPorNota(5.0).getQuantidade());
        assertEquals(0, QuantidadeCursos.calcularPorNota(null).getQuantidade());
    }

    @Test
    @DisplayName("Deve retornar true/false para temBonus corretamente")
    public void deveRetornarTemBonusCorretamente() {
        // Arrange & Act & Assert
        assertTrue(new QuantidadeCursos(3).temBonus());
        assertFalse(new QuantidadeCursos(0).temBonus());
        assertFalse(new QuantidadeCursos(null).temBonus());
    }
}
