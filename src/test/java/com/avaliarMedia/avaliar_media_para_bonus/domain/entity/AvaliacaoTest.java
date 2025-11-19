package com.avaliarMedia.avaliar_media_para_bonus.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a entidade Avaliacao.
 */
@DisplayName("Testes Unitários - Avaliacao Entity")
public class AvaliacaoTest {

    private Aluno aluno;

    @BeforeEach
    public void setup() {
        aluno = Aluno.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .build();
    }

    @Test
    @DisplayName("Deve criar avaliação usando builder")
    public void deveCriarAvaliacaoUsandoBuilder() {
        // Arrange & Act
        Avaliacao avaliacao = Avaliacao.builder()
                .id(1L)
                .aluno(aluno)
                .nota(8.5)
                .build();

        // Assert
        assertNotNull(avaliacao);
        assertEquals(1L, avaliacao.getId());
        assertEquals(aluno, avaliacao.getAluno());
        assertEquals(8.5, avaliacao.getNota());
    }

    @Test
    @DisplayName("Deve implementar equals corretamente (comparação por ID)")
    public void deveImplementarEqualsCorretamente() {
        // Arrange
        Avaliacao avaliacao1 = Avaliacao.builder()
                .id(1L)
                .aluno(aluno)
                .nota(8.5)
                .build();

        Avaliacao avaliacao2 = Avaliacao.builder()
                .id(1L)
                .aluno(aluno)
                .nota(8.5)
                .build();

        Avaliacao avaliacao3 = Avaliacao.builder()
                .id(2L)
                .aluno(aluno)
                .nota(8.5)
                .build();

        // Act & Assert
        assertEquals(avaliacao1, avaliacao2); // Mesmo ID = mesma entidade
        assertNotEquals(avaliacao1, avaliacao3); // IDs diferentes = entidades diferentes
    }

    @Test
    @DisplayName("Deve implementar hashCode corretamente")
    public void deveImplementarHashCodeCorretamente() {
        // Arrange
        Avaliacao avaliacao1 = Avaliacao.builder()
                .id(1L)
                .aluno(aluno)
                .nota(8.5)
                .build();

        Avaliacao avaliacao2 = Avaliacao.builder()
                .id(1L)
                .aluno(aluno)
                .nota(8.5)
                .build();

        // Act & Assert
        assertEquals(avaliacao1.hashCode(), avaliacao2.hashCode());
    }
}
