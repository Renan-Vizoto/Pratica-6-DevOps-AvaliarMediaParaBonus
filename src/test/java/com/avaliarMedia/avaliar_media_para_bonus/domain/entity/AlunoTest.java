package com.avaliarMedia.avaliar_media_para_bonus.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a entidade Aluno.
 */
@DisplayName("Testes Unitários - Aluno Entity")
public class AlunoTest {

    @Test
    @DisplayName("Deve criar aluno usando builder")
    public void deveCriarAlunoUsandoBuilder() {
        // Arrange & Act
        Aluno aluno = Aluno.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .build();

        // Assert
        assertNotNull(aluno);
        assertEquals(1L, aluno.getId());
        assertEquals("João Silva", aluno.getNome());
        assertEquals("joao@email.com", aluno.getEmail());
    }

    @Test
    @DisplayName("Deve implementar equals corretamente (comparação por ID)")
    public void deveImplementarEqualsCorretamente() {
        // Arrange
        Aluno aluno1 = Aluno.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .build();

        Aluno aluno2 = Aluno.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .build();

        Aluno aluno3 = Aluno.builder()
                .id(2L)
                .nome("João Silva")
                .email("joao@email.com")
                .build();

        // Act & Assert
        assertEquals(aluno1, aluno2); // Mesmo ID = mesma entidade
        assertNotEquals(aluno1, aluno3); // IDs diferentes = entidades diferentes
    }

    @Test
    @DisplayName("Deve implementar hashCode corretamente")
    public void deveImplementarHashCodeCorretamente() {
        // Arrange
        Aluno aluno1 = Aluno.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .build();

        Aluno aluno2 = Aluno.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .build();

        // Act & Assert
        assertEquals(aluno1.hashCode(), aluno2.hashCode());
    }
}
