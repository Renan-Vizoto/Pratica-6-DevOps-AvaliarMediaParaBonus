package com.avaliarMedia.avaliar_media_para_bonus.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a entidade Aluno.
 * 
 * IMPORTÂNCIA DE TESTAR ENTIDADES:
 * - Validação de regras de negócio básicas
 * - Verificação de equals/hashCode (crítico para collections)
 * - Teste de builder pattern
 * - Garantia de imutabilidade quando necessário
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
    @DisplayName("Deve criar aluno sem ID (novo aluno)")
    public void deveCriarAlunoSemId() {
        // Arrange & Act
        Aluno aluno = Aluno.builder()
                .nome("Maria Santos")
                .email("maria@email.com")
                .build();

        // Assert
        assertNotNull(aluno);
        assertNull(aluno.getId());
        assertEquals("Maria Santos", aluno.getNome());
        assertEquals("maria@email.com", aluno.getEmail());
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

    @Test
    @DisplayName("Deve implementar toString corretamente")
    public void deveImplementarToStringCorretamente() {
        // Arrange
        Aluno aluno = Aluno.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .build();

        // Act
        String toString = aluno.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("Aluno"));
    }

    @Test
    @DisplayName("Deve permitir modificar nome e email após criação")
    public void devePermitirModificarNomeEEmailAposCriacao() {
        // Arrange
        Aluno aluno = Aluno.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .build();

        // Act
        aluno.setNome("João Santos");
        aluno.setEmail("joao.santos@email.com");

        // Assert
        assertEquals("João Santos", aluno.getNome());
        assertEquals("joao.santos@email.com", aluno.getEmail());
    }

    @Test
    @DisplayName("Deve criar aluno usando construtor sem argumentos")
    public void deveCriarAlunoUsandoConstrutorSemArgumentos() {
        // Arrange & Act
        Aluno aluno = new Aluno();

        // Assert
        assertNotNull(aluno);
        assertNull(aluno.getId());
        assertNull(aluno.getNome());
        assertNull(aluno.getEmail());
    }

    @Test
    @DisplayName("Deve criar aluno usando construtor com todos os argumentos")
    public void deveCriarAlunoUsandoConstrutorComTodosArgumentos() {
        // Arrange & Act
        Aluno aluno = new Aluno(1L, "João Silva", "joao@email.com");

        // Assert
        assertNotNull(aluno);
        assertEquals(1L, aluno.getId());
        assertEquals("João Silva", aluno.getNome());
        assertEquals("joao@email.com", aluno.getEmail());
    }

    @Test
    @DisplayName("Deve comparar alunos nulos corretamente")
    public void deveCompararAlunosNulosCorretamente() {
        // Arrange
        Aluno aluno1 = Aluno.builder().id(1L).build();
        Aluno aluno2 = null;

        // Act & Assert
        assertNotEquals(aluno1, aluno2);
        assertFalse(aluno1.equals(aluno2));
    }
}

