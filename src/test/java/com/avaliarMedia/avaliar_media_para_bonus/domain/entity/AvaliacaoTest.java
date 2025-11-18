package com.avaliarMedia.avaliar_media_para_bonus.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a entidade Avaliacao.
 * 
 * IMPORTÂNCIA DE TESTAR ENTIDADES:
 * - Validação de relacionamentos (Aluno)
 * - Verificação de equals/hashCode baseado em ID
 * - Teste de builder pattern com relacionamentos
 * - Garantia de integridade dos dados
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
    @DisplayName("Deve criar avaliação sem ID (nova avaliação)")
    public void deveCriarAvaliacaoSemId() {
        // Arrange & Act
        Avaliacao avaliacao = Avaliacao.builder()
                .aluno(aluno)
                .nota(8.5)
                .build();

        // Assert
        assertNotNull(avaliacao);
        assertNull(avaliacao.getId());
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

    @Test
    @DisplayName("Deve implementar toString corretamente")
    public void deveImplementarToStringCorretamente() {
        // Arrange
        Avaliacao avaliacao = Avaliacao.builder()
                .id(1L)
                .aluno(aluno)
                .nota(8.5)
                .build();

        // Act
        String toString = avaliacao.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("Avaliacao"));
    }

    @Test
    @DisplayName("Deve permitir modificar nota após criação")
    public void devePermitirModificarNotaAposCriacao() {
        // Arrange
        Avaliacao avaliacao = Avaliacao.builder()
                .id(1L)
                .aluno(aluno)
                .nota(8.5)
                .build();

        // Act
        avaliacao.setNota(9.0);

        // Assert
        assertEquals(9.0, avaliacao.getNota());
    }

    @Test
    @DisplayName("Deve permitir modificar aluno após criação")
    public void devePermitirModificarAlunoAposCriacao() {
        // Arrange
        Avaliacao avaliacao = Avaliacao.builder()
                .id(1L)
                .aluno(aluno)
                .nota(8.5)
                .build();

        Aluno novoAluno = Aluno.builder()
                .id(2L)
                .nome("Maria Santos")
                .email("maria@email.com")
                .build();

        // Act
        avaliacao.setAluno(novoAluno);

        // Assert
        assertEquals(novoAluno, avaliacao.getAluno());
        assertEquals(2L, avaliacao.getAluno().getId());
    }

    @Test
    @DisplayName("Deve criar avaliação usando construtor sem argumentos")
    public void deveCriarAvaliacaoUsandoConstrutorSemArgumentos() {
        // Arrange & Act
        Avaliacao avaliacao = new Avaliacao();

        // Assert
        assertNotNull(avaliacao);
        assertNull(avaliacao.getId());
        assertNull(avaliacao.getAluno());
        assertNull(avaliacao.getNota());
    }

    @Test
    @DisplayName("Deve criar avaliação usando construtor com todos os argumentos")
    public void deveCriarAvaliacaoUsandoConstrutorComTodosArgumentos() {
        // Arrange & Act
        Avaliacao avaliacao = new Avaliacao(1L, aluno, 8.5);

        // Assert
        assertNotNull(avaliacao);
        assertEquals(1L, avaliacao.getId());
        assertEquals(aluno, avaliacao.getAluno());
        assertEquals(8.5, avaliacao.getNota());
    }

    @Test
    @DisplayName("Deve criar avaliação com nota zero")
    public void deveCriarAvaliacaoComNotaZero() {
        // Arrange & Act
        Avaliacao avaliacao = Avaliacao.builder()
                .id(1L)
                .aluno(aluno)
                .nota(0.0)
                .build();

        // Assert
        assertNotNull(avaliacao);
        assertEquals(0.0, avaliacao.getNota());
    }

    @Test
    @DisplayName("Deve criar avaliação com nota máxima")
    public void deveCriarAvaliacaoComNotaMaxima() {
        // Arrange & Act
        Avaliacao avaliacao = Avaliacao.builder()
                .id(1L)
                .aluno(aluno)
                .nota(10.0)
                .build();

        // Assert
        assertNotNull(avaliacao);
        assertEquals(10.0, avaliacao.getNota());
    }
}

