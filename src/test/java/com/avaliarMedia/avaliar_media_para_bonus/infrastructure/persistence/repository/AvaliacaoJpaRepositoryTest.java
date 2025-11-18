package com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.repository;

import com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.entity.AlunoEntity;
import com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.entity.AvaliacaoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de Repository JPA para AvaliacaoJpaRepository usando @DataJpaTest.
 * 
 * VANTAGENS DO @DataJpaTest:
 * - Testa apenas a camada de persistência (JPA/Hibernate)
 * - Usa banco H2 em memória (não precisa de MySQL rodando)
 * - Testa queries, relacionamentos, constraints
 * - Rollback automático após cada teste
 * - Execução rápida comparada a testes de integração completos
 */
@DataJpaTest
@DisplayName("Testes JPA - AvaliacaoJpaRepository")
public class AvaliacaoJpaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AvaliacaoJpaRepository avaliacaoJpaRepository;

    private AlunoEntity aluno;

    @BeforeEach
    public void setup() {
        aluno = AlunoEntity.builder()
                .nome("João Silva")
                .email("joao@email.com")
                .build();
        
        aluno = entityManager.persistAndFlush(aluno);
    }

    @Test
    @DisplayName("Deve salvar avaliação no banco de dados")
    public void deveSalvarAvaliacaoNoBancoDeDados() {
        // Arrange
        AvaliacaoEntity avaliacao = AvaliacaoEntity.builder()
                .aluno(aluno)
                .nota(8.5)
                .build();

        // Act
        AvaliacaoEntity avaliacaoSalva = avaliacaoJpaRepository.save(avaliacao);

        // Assert
        assertNotNull(avaliacaoSalva.getId());
        assertEquals(8.5, avaliacaoSalva.getNota());
        assertEquals(aluno.getId(), avaliacaoSalva.getAluno().getId());
        
        // Verifica se foi persistido no banco
        AvaliacaoEntity encontrada = entityManager.find(AvaliacaoEntity.class, avaliacaoSalva.getId());
        assertNotNull(encontrada);
        assertEquals(8.5, encontrada.getNota());
    }

    @Test
    @DisplayName("Deve buscar avaliação por ID")
    public void deveBuscarAvaliacaoPorId() {
        // Arrange
        AvaliacaoEntity avaliacao = AvaliacaoEntity.builder()
                .aluno(aluno)
                .nota(8.5)
                .build();
        
        AvaliacaoEntity avaliacaoSalva = entityManager.persistAndFlush(avaliacao);

        // Act
        Optional<AvaliacaoEntity> resultado = avaliacaoJpaRepository.findById(avaliacaoSalva.getId());

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(8.5, resultado.get().getNota());
        assertEquals(aluno.getId(), resultado.get().getAluno().getId());
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando avaliação não encontrada")
    public void deveRetornarOptionalVazioQuandoAvaliacaoNaoEncontrada() {
        // Act
        Optional<AvaliacaoEntity> resultado = avaliacaoJpaRepository.findById(999L);

        // Assert
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Deve buscar avaliação por aluno ID usando query method")
    public void deveBuscarAvaliacaoPorAlunoIdUsandoQueryMethod() {
        // Arrange
        AvaliacaoEntity avaliacao = AvaliacaoEntity.builder()
                .aluno(aluno)
                .nota(8.5)
                .build();
        
        entityManager.persistAndFlush(avaliacao);

        // Act
        Optional<AvaliacaoEntity> resultado = avaliacaoJpaRepository.findByAlunoId(aluno.getId());

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(8.5, resultado.get().getNota());
        assertEquals(aluno.getId(), resultado.get().getAluno().getId());
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando aluno não tem avaliação")
    public void deveRetornarOptionalVazioQuandoAlunoNaoTemAvaliacao() {
        // Act
        Optional<AvaliacaoEntity> resultado = avaliacaoJpaRepository.findByAlunoId(999L);

        // Assert
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Deve deletar avaliação por ID")
    public void deveDeletarAvaliacaoPorId() {
        // Arrange
        AvaliacaoEntity avaliacao = AvaliacaoEntity.builder()
                .aluno(aluno)
                .nota(8.5)
                .build();
        
        AvaliacaoEntity avaliacaoSalva = entityManager.persistAndFlush(avaliacao);
        Long id = avaliacaoSalva.getId();

        // Act
        avaliacaoJpaRepository.deleteById(id);
        entityManager.flush();

        // Assert
        AvaliacaoEntity encontrada = entityManager.find(AvaliacaoEntity.class, id);
        assertNull(encontrada);
    }

    @Test
    @DisplayName("Deve validar constraint de nota não nula")
    public void deveValidarConstraintDeNotaNaoNula() {
        // Arrange
        AvaliacaoEntity avaliacao = AvaliacaoEntity.builder()
                .aluno(aluno)
                .build();

        // Act & Assert
        assertThrows(Exception.class, () -> {
            avaliacaoJpaRepository.saveAndFlush(avaliacao);
        });
    }

    @Test
    @DisplayName("Deve validar constraint de aluno não nulo")
    public void deveValidarConstraintDeAlunoNaoNulo() {
        // Arrange
        AvaliacaoEntity avaliacao = AvaliacaoEntity.builder()
                .nota(8.5)
                .build();

        // Act & Assert
        assertThrows(Exception.class, () -> {
            avaliacaoJpaRepository.saveAndFlush(avaliacao);
        });
    }

    @Test
    @DisplayName("Deve validar constraint de relacionamento 1:1 (um aluno, uma avaliação)")
    public void deveValidarConstraintDeRelacionamento1Para1() {
        // Arrange
        AvaliacaoEntity avaliacao1 = AvaliacaoEntity.builder()
                .aluno(aluno)
                .nota(8.5)
                .build();
        
        entityManager.persistAndFlush(avaliacao1);

        AvaliacaoEntity avaliacao2 = AvaliacaoEntity.builder()
                .aluno(aluno) // Mesmo aluno
                .nota(9.0)
                .build();

        // Act & Assert - Deve falhar porque aluno_id é unique
        assertThrows(DataIntegrityViolationException.class, () -> {
            avaliacaoJpaRepository.saveAndFlush(avaliacao2);
        });
    }

    @Test
    @DisplayName("Deve atualizar avaliação existente")
    public void deveAtualizarAvaliacaoExistente() {
        // Arrange
        AvaliacaoEntity avaliacao = AvaliacaoEntity.builder()
                .aluno(aluno)
                .nota(8.5)
                .build();
        
        AvaliacaoEntity avaliacaoSalva = entityManager.persistAndFlush(avaliacao);

        // Act
        avaliacaoSalva.setNota(9.0);
        AvaliacaoEntity avaliacaoAtualizada = avaliacaoJpaRepository.saveAndFlush(avaliacaoSalva);

        // Assert
        assertEquals(9.0, avaliacaoAtualizada.getNota());
        
        AvaliacaoEntity encontrada = entityManager.find(AvaliacaoEntity.class, avaliacaoSalva.getId());
        assertEquals(9.0, encontrada.getNota());
    }

    @Test
    @DisplayName("Deve manter relacionamento com aluno após salvar")
    public void deveManterRelacionamentoComAlunoAposSalvar() {
        // Arrange
        AvaliacaoEntity avaliacao = AvaliacaoEntity.builder()
                .aluno(aluno)
                .nota(8.5)
                .build();

        // Act
        AvaliacaoEntity avaliacaoSalva = avaliacaoJpaRepository.saveAndFlush(avaliacao);

        // Assert
        assertNotNull(avaliacaoSalva.getAluno());
        assertEquals(aluno.getId(), avaliacaoSalva.getAluno().getId());
        assertEquals("João Silva", avaliacaoSalva.getAluno().getNome());
    }

    @Test
    @DisplayName("Deve listar todas as avaliações")
    public void deveListarTodasAsAvaliacoes() {
        // Arrange
        AlunoEntity aluno2 = AlunoEntity.builder()
                .nome("Maria Santos")
                .email("maria@email.com")
                .build();
        aluno2 = entityManager.persistAndFlush(aluno2);

        AvaliacaoEntity avaliacao1 = AvaliacaoEntity.builder()
                .aluno(aluno)
                .nota(8.5)
                .build();
        
        AvaliacaoEntity avaliacao2 = AvaliacaoEntity.builder()
                .aluno(aluno2)
                .nota(9.0)
                .build();
        
        entityManager.persistAndFlush(avaliacao1);
        entityManager.persistAndFlush(avaliacao2);

        // Act
        var avaliacoes = avaliacaoJpaRepository.findAll();

        // Assert
        assertTrue(avaliacoes.size() >= 2);
        assertTrue(avaliacoes.stream().anyMatch(a -> a.getNota().equals(8.5)));
        assertTrue(avaliacoes.stream().anyMatch(a -> a.getNota().equals(9.0)));
    }
}

