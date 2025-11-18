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
 * 
 * IMPORTÂNCIA DO @DataJpaTest:
 * 
 * @DataJpaTest (Teste Focado em JPA):
 * - ✅ Mais rápido: carrega apenas camada de persistência, não contexto completo
 * - ✅ Banco em memória: usa H2 automaticamente (não precisa configurar MySQL)
 * - ✅ Transacional: rollback automático após cada teste
 * - ✅ Foco: testa queries JPA, relacionamentos, constraints, mapeamentos
 * - ✅ TestEntityManager: permite manipular entidades diretamente
 * 
 * @SpringBootTest (Teste de Integração):
 * - ❌ Mais lento: carrega contexto completo da aplicação
 * - ❌ Mais complexo: precisa configurar banco de dados real
 * - ❌ Testa tudo: todas as camadas juntas
 * 
 * REGRA: Use @DataJpaTest para testar queries e mapeamentos JPA,
 * e @SpringBootTest para validar fluxo completo end-to-end.
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
        
        // Verifica se foi persistido no banco
        AlunoEntity encontrado = entityManager.find(AlunoEntity.class, alunoSalvo.getId());
        assertNotNull(encontrado);
        assertEquals("João Silva", encontrado.getNome());
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
        assertEquals("joao@email.com", resultado.get().getEmail());
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando aluno não encontrado")
    public void deveRetornarOptionalVazioQuandoAlunoNaoEncontrado() {
        // Act
        Optional<AlunoEntity> resultado = alunoJpaRepository.findById(999L);

        // Assert
        assertFalse(resultado.isPresent());
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
        assertEquals("joao@email.com", resultado.get().getEmail());
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando email não encontrado")
    public void deveRetornarOptionalVazioQuandoEmailNaoEncontrado() {
        // Act
        Optional<AlunoEntity> resultado = alunoJpaRepository.findByEmail("naoexiste@email.com");

        // Assert
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Deve deletar aluno por ID")
    public void deveDeletarAlunoPorId() {
        // Arrange
        AlunoEntity aluno = AlunoEntity.builder()
                .nome("João Silva")
                .email("joao@email.com")
                .build();
        
        AlunoEntity alunoSalvo = entityManager.persistAndFlush(aluno);
        Long id = alunoSalvo.getId();

        // Act
        alunoJpaRepository.deleteById(id);
        entityManager.flush();

        // Assert
        AlunoEntity encontrado = entityManager.find(AlunoEntity.class, id);
        assertNull(encontrado);
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

    @Test
    @DisplayName("Deve validar constraint de nome não nulo")
    public void deveValidarConstraintDeNomeNaoNulo() {
        // Arrange
        AlunoEntity aluno = AlunoEntity.builder()
                .email("joao@email.com")
                .build();

        // Act & Assert
        assertThrows(Exception.class, () -> {
            alunoJpaRepository.saveAndFlush(aluno);
        });
    }

    @Test
    @DisplayName("Deve validar constraint de email não nulo")
    public void deveValidarConstraintDeEmailNaoNulo() {
        // Arrange
        AlunoEntity aluno = AlunoEntity.builder()
                .nome("João Silva")
                .build();

        // Act & Assert
        assertThrows(Exception.class, () -> {
            alunoJpaRepository.saveAndFlush(aluno);
        });
    }

    @Test
    @DisplayName("Deve atualizar aluno existente")
    public void deveAtualizarAlunoExistente() {
        // Arrange
        AlunoEntity aluno = AlunoEntity.builder()
                .nome("João Silva")
                .email("joao@email.com")
                .build();
        
        AlunoEntity alunoSalvo = entityManager.persistAndFlush(aluno);

        // Act
        alunoSalvo.setNome("João Santos");
        alunoSalvo.setEmail("joao.santos@email.com");
        AlunoEntity alunoAtualizado = alunoJpaRepository.saveAndFlush(alunoSalvo);

        // Assert
        assertEquals("João Santos", alunoAtualizado.getNome());
        assertEquals("joao.santos@email.com", alunoAtualizado.getEmail());
        
        AlunoEntity encontrado = entityManager.find(AlunoEntity.class, alunoSalvo.getId());
        assertEquals("João Santos", encontrado.getNome());
    }

    @Test
    @DisplayName("Deve listar todos os alunos")
    public void deveListarTodosOsAlunos() {
        // Arrange
        AlunoEntity aluno1 = AlunoEntity.builder()
                .nome("João Silva")
                .email("joao@email.com")
                .build();
        
        AlunoEntity aluno2 = AlunoEntity.builder()
                .nome("Maria Santos")
                .email("maria@email.com")
                .build();
        
        entityManager.persistAndFlush(aluno1);
        entityManager.persistAndFlush(aluno2);

        // Act
        var alunos = alunoJpaRepository.findAll();

        // Assert
        assertTrue(alunos.size() >= 2);
        assertTrue(alunos.stream().anyMatch(a -> a.getNome().equals("João Silva")));
        assertTrue(alunos.stream().anyMatch(a -> a.getNome().equals("Maria Santos")));
    }
}

