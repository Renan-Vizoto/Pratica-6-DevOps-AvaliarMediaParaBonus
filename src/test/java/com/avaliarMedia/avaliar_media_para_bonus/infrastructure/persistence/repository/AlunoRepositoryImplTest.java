package com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.repository;

import com.avaliarMedia.avaliar_media_para_bonus.domain.entity.Aluno;
import com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.entity.AlunoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para AlunoRepositoryImpl.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários - AlunoRepositoryImpl")
public class AlunoRepositoryImplTest {

    @Mock
    private AlunoJpaRepository jpaRepository;

    @InjectMocks
    private AlunoRepositoryImpl alunoRepository;

    private Aluno aluno;
    private AlunoEntity alunoEntity;

    @BeforeEach
    public void setup() {
        aluno = Aluno.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .build();

        alunoEntity = AlunoEntity.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .build();
    }

    @Test
    @DisplayName("Deve salvar aluno com sucesso")
    public void deveSalvarAlunoComSucesso() {
        // Arrange
        when(jpaRepository.save(any(AlunoEntity.class))).thenReturn(alunoEntity);

        // Act
        Aluno resultado = alunoRepository.save(aluno);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@email.com", resultado.getEmail());
        verify(jpaRepository, times(1)).save(any(AlunoEntity.class));
    }

    @Test
    @DisplayName("Deve buscar aluno por ID com sucesso")
    public void deveBuscarAlunoPorIdComSucesso() {
        // Arrange
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(alunoEntity));

        // Act
        Optional<Aluno> resultado = alunoRepository.findById(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        assertEquals("João Silva", resultado.get().getNome());
        verify(jpaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando aluno não encontrado por ID")
    public void deveRetornarOptionalVazioQuandoAlunoNaoEncontradoPorId() {
        // Arrange
        when(jpaRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Aluno> resultado = alunoRepository.findById(999L);

        // Assert
        assertFalse(resultado.isPresent());
        verify(jpaRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Deve buscar aluno por email com sucesso")
    public void deveBuscarAlunoPorEmailComSucesso() {
        // Arrange
        when(jpaRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(alunoEntity));

        // Act
        Optional<Aluno> resultado = alunoRepository.findByEmail("joao@email.com");

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("joao@email.com", resultado.get().getEmail());
        verify(jpaRepository, times(1)).findByEmail("joao@email.com");
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando aluno não encontrado por email")
    public void deveRetornarOptionalVazioQuandoAlunoNaoEncontradoPorEmail() {
        // Arrange
        when(jpaRepository.findByEmail("naoexiste@email.com")).thenReturn(Optional.empty());

        // Act
        Optional<Aluno> resultado = alunoRepository.findByEmail("naoexiste@email.com");

        // Assert
        assertFalse(resultado.isPresent());
        verify(jpaRepository, times(1)).findByEmail("naoexiste@email.com");
    }

    @Test
    @DisplayName("Deve deletar aluno por ID com sucesso")
    public void deveDeletarAlunoPorIdComSucesso() {
        // Arrange
        doNothing().when(jpaRepository).deleteById(1L);

        // Act
        alunoRepository.deleteById(1L);

        // Assert
        verify(jpaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve salvar aluno sem ID")
    public void deveSalvarAlunoSemId() {
        // Arrange
        Aluno alunoSemId = Aluno.builder()
                .nome("Maria Santos")
                .email("maria@email.com")
                .build();

        AlunoEntity entitySalva = AlunoEntity.builder()
                .id(2L)
                .nome("Maria Santos")
                .email("maria@email.com")
                .build();

        when(jpaRepository.save(any(AlunoEntity.class))).thenReturn(entitySalva);

        // Act
        Aluno resultado = alunoRepository.save(alunoSemId);

        // Assert
        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
        assertEquals("Maria Santos", resultado.getNome());
        verify(jpaRepository, times(1)).save(any(AlunoEntity.class));
    }
}

