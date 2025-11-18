package com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.repository;

import com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.entity.AlunoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de Repository JPA para AlunoJpaRepository usando @DataJpaTest.
 */
@DataJpaTest
@DisplayName("Testes JPA - AlunoJpaRepository")
public class AlunoJpaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AlunoJpaRepository alunoJpaRepository;

    @Test
    @DisplayName("Deve salvar aluno no banco de dados")
    public void deveSalvarAlunoNoBancoDeDados() {
        // Arrange
        AlunoEntity aluno = AlunoEntity.builder()
                .nome("João Silva")
                .email("joao@email.com")
                .build();

        // Act
        AlunoEntity alunoSalvo = alunoJpaRepository.save(aluno);

        // Assert
        assertNotNull(alunoSalvo.getId());
        assertEquals("João Silva", alunoSalvo.getNome());
        assertEquals("joao@email.com", alunoSalvo.getEmail());
    }

    @Test
    @DisplayName("Deve buscar aluno por ID")
    public void deveBuscarAlunoPorId() {
        // Arrange
        AlunoEntity aluno = AlunoEntity.builder()
                .nome("João Silva")
                .email("joao@email.com")
                .build();
        
        AlunoEntity alunoSalvo = entityManager.persistAndFlush(aluno);

        // Act
        Optional<AlunoEntity> resultado = alunoJpaRepository.findById(alunoSalvo.getId());

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("João Silva", resultado.get().getNome());
    }

    @Test
    @DisplayName("Deve buscar aluno por email usando query method")
    public void deveBuscarAlunoPorEmailUsandoQueryMethod() {
        // Arrange
        AlunoEntity aluno = AlunoEntity.builder()
                .nome("João Silva")
                .email("joao@email.com")
                .build();
        
        entityManager.persistAndFlush(aluno);

        // Act
        Optional<AlunoEntity> resultado = alunoJpaRepository.findByEmail("joao@email.com");

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("João Silva", resultado.get().getNome());
    }

    @Test
    @DisplayName("Deve validar constraint de email único")
    public void deveValidarConstraintDeEmailUnico() {
        // Arrange
        AlunoEntity aluno1 = AlunoEntity.builder()
                .nome("João Silva")
                .email("joao@email.com")
                .build();
        
        entityManager.persistAndFlush(aluno1);

        AlunoEntity aluno2 = AlunoEntity.builder()
                .nome("Maria Santos")
                .email("joao@email.com") // Mesmo email
                .build();

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class, () -> {
            alunoJpaRepository.saveAndFlush(aluno2);
        });
    }
}
