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
        // Arrange & Act
        QuantidadeCursos quantidade1 = QuantidadeCursos.calcularPorNota(7.1);
        QuantidadeCursos quantidade2 = QuantidadeCursos.calcularPorNota(8.0);
        QuantidadeCursos quantidade3 = QuantidadeCursos.calcularPorNota(10.0);

        // Assert
        assertEquals(3, quantidade1.getQuantidade());
        assertEquals(3, quantidade2.getQuantidade());
        assertEquals(3, quantidade3.getQuantidade());
    }

    @Test
    @DisplayName("Deve calcular 0 cursos bônus para nota igual a 7.0")
    public void deveCalcular0CursosBonusParaNotaIgual7() {
        // Arrange & Act
        QuantidadeCursos quantidade = QuantidadeCursos.calcularPorNota(7.0);

        // Assert
        assertEquals(0, quantidade.getQuantidade());
    }

    @Test
    @DisplayName("Deve calcular 0 cursos bônus para nota menor que 7.0")
    public void deveCalcular0CursosBonusParaNotaMenorQue7() {
        // Arrange & Act
        QuantidadeCursos quantidade1 = QuantidadeCursos.calcularPorNota(0.0);
        QuantidadeCursos quantidade2 = QuantidadeCursos.calcularPorNota(5.0);
        QuantidadeCursos quantidade3 = QuantidadeCursos.calcularPorNota(6.9);

        // Assert
        assertEquals(0, quantidade1.getQuantidade());
        assertEquals(0, quantidade2.getQuantidade());
        assertEquals(0, quantidade3.getQuantidade());
    }

    @Test
    @DisplayName("Deve calcular 0 cursos bônus para nota null")
    public void deveCalcular0CursosBonusParaNotaNull() {
        // Arrange & Act
        QuantidadeCursos quantidade = QuantidadeCursos.calcularPorNota(null);

        // Assert
        assertEquals(0, quantidade.getQuantidade());
    }

    @Test
    @DisplayName("Deve retornar true para temBonus quando quantidade maior que 0")
    public void deveRetornarTrueParaTemBonusQuandoQuantidadeMaiorQue0() {
        // Arrange
        QuantidadeCursos quantidade = new QuantidadeCursos(3);

        // Act & Assert
        assertTrue(quantidade.temBonus());
    }

    @Test
    @DisplayName("Deve retornar false para temBonus quando quantidade é 0")
    public void deveRetornarFalseParaTemBonusQuandoQuantidadeZero() {
        // Arrange
        QuantidadeCursos quantidade = new QuantidadeCursos(0);

        // Act & Assert
        assertFalse(quantidade.temBonus());
    }

    @Test
    @DisplayName("Deve retornar false para temBonus quando quantidade é null")
    public void deveRetornarFalseParaTemBonusQuandoQuantidadeNull() {
        // Arrange
        QuantidadeCursos quantidade = new QuantidadeCursos(null);

        // Act & Assert
        assertFalse(quantidade.temBonus());
    }

    @Test
    @DisplayName("Deve implementar equals corretamente")
    public void deveImplementarEqualsCorretamente() {
        // Arrange
        QuantidadeCursos quantidade1 = new QuantidadeCursos(3);
        QuantidadeCursos quantidade2 = new QuantidadeCursos(3);
        QuantidadeCursos quantidade3 = new QuantidadeCursos(0);

        // Act & Assert
        assertEquals(quantidade1, quantidade2);
        assertNotEquals(quantidade1, quantidade3);
    }

    @Test
    @DisplayName("Deve implementar hashCode corretamente")
    public void deveImplementarHashCodeCorretamente() {
        // Arrange
        QuantidadeCursos quantidade1 = new QuantidadeCursos(3);
        QuantidadeCursos quantidade2 = new QuantidadeCursos(3);

        // Act & Assert
        assertEquals(quantidade1.hashCode(), quantidade2.hashCode());
    }

    @Test
    @DisplayName("Deve implementar toString corretamente")
    public void deveImplementarToStringCorretamente() {
        // Arrange
        QuantidadeCursos quantidade = new QuantidadeCursos(3);

        // Act
        String toString = quantidade.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("3"));
    }
}

