package com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.repository;

import com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.entity.AlunoEntity;
import com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.entity.AvaliacaoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de Repository JPA para AvaliacaoJpaRepository usando @DataJpaTest.
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
    }

    @Test
    @DisplayName("Deve buscar avaliação por alunoId")
    public void deveBuscarAvaliacaoPorAlunoId() {
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
}
