package com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.repository;

import com.avaliarMedia.avaliar_media_para_bonus.domain.entity.Aluno;
import com.avaliarMedia.avaliar_media_para_bonus.domain.entity.Avaliacao;
import com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.entity.AlunoEntity;
import com.avaliarMedia.avaliar_media_para_bonus.infrastructure.persistence.entity.AvaliacaoEntity;
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
 * Testes unitários para AvaliacaoRepositoryImpl.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários - AvaliacaoRepositoryImpl")
public class AvaliacaoRepositoryImplTest {

    @Mock
    private AvaliacaoJpaRepository jpaRepository;

    @Mock
    private AlunoJpaRepository alunoJpaRepository;

    @InjectMocks
    private AvaliacaoRepositoryImpl avaliacaoRepository;

    private Aluno aluno;
    private AlunoEntity alunoEntity;
    private Avaliacao avaliacao;
    private AvaliacaoEntity avaliacaoEntity;

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

        avaliacao = Avaliacao.builder()
                .id(1L)
                .aluno(aluno)
                .nota(8.5)
                .build();

        avaliacaoEntity = AvaliacaoEntity.builder()
                .id(1L)
                .aluno(alunoEntity)
                .nota(8.5)
                .build();
    }

    @Test
    @DisplayName("Deve salvar avaliação com sucesso")
    public void deveSalvarAvaliacaoComSucesso() {
        // Arrange
        when(alunoJpaRepository.findById(1L)).thenReturn(Optional.of(alunoEntity));
        when(jpaRepository.save(any(AvaliacaoEntity.class))).thenReturn(avaliacaoEntity);

        // Act
        Avaliacao resultado = avaliacaoRepository.save(avaliacao);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(8.5, resultado.getNota());
        assertEquals("João Silva", resultado.getAluno().getNome());
        verify(alunoJpaRepository, times(1)).findById(1L);
        verify(jpaRepository, times(1)).save(any(AvaliacaoEntity.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar avaliação com aluno não encontrado")
    public void deveLancarExcecaoAoSalvarAvaliacaoComAlunoNaoEncontrado() {
        // Arrange
        when(alunoJpaRepository.findById(999L)).thenReturn(Optional.empty());
        
        Avaliacao avaliacaoComAlunoInexistente = Avaliacao.builder()
                .aluno(Aluno.builder().id(999L).build())
                .nota(8.5)
                .build();

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            avaliacaoRepository.save(avaliacaoComAlunoInexistente);
        });

        assertEquals("Aluno não encontrado", exception.getMessage());
        verify(alunoJpaRepository, times(1)).findById(999L);
        verify(jpaRepository, never()).save(any(AvaliacaoEntity.class));
    }

    @Test
    @DisplayName("Deve buscar avaliação por ID com sucesso")
    public void deveBuscarAvaliacaoPorIdComSucesso() {
        // Arrange
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(avaliacaoEntity));

        // Act
        Optional<Avaliacao> resultado = avaliacaoRepository.findById(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        assertEquals(8.5, resultado.get().getNota());
        verify(jpaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando avaliação não encontrada por ID")
    public void deveRetornarOptionalVazioQuandoAvaliacaoNaoEncontradaPorId() {
        // Arrange
        when(jpaRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Avaliacao> resultado = avaliacaoRepository.findById(999L);

        // Assert
        assertFalse(resultado.isPresent());
        verify(jpaRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Deve buscar avaliação por aluno ID com sucesso")
    public void deveBuscarAvaliacaoPorAlunoIdComSucesso() {
        // Arrange
        when(jpaRepository.findByAlunoId(1L)).thenReturn(Optional.of(avaliacaoEntity));

        // Act
        Optional<Avaliacao> resultado = avaliacaoRepository.findByAlunoId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getAluno().getId());
        assertEquals(8.5, resultado.get().getNota());
        verify(jpaRepository, times(1)).findByAlunoId(1L);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando avaliação não encontrada por aluno ID")
    public void deveRetornarOptionalVazioQuandoAvaliacaoNaoEncontradaPorAlunoId() {
        // Arrange
        when(jpaRepository.findByAlunoId(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Avaliacao> resultado = avaliacaoRepository.findByAlunoId(999L);

        // Assert
        assertFalse(resultado.isPresent());
        verify(jpaRepository, times(1)).findByAlunoId(999L);
    }

    @Test
    @DisplayName("Deve deletar avaliação por ID com sucesso")
    public void deveDeletarAvaliacaoPorIdComSucesso() {
        // Arrange
        doNothing().when(jpaRepository).deleteById(1L);

        // Act
        avaliacaoRepository.deleteById(1L);

        // Assert
        verify(jpaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve salvar avaliação sem ID")
    public void deveSalvarAvaliacaoSemId() {
        // Arrange
        Avaliacao avaliacaoSemId = Avaliacao.builder()
                .aluno(aluno)
                .nota(9.0)
                .build();

        AvaliacaoEntity entitySalva = AvaliacaoEntity.builder()
                .id(2L)
                .aluno(alunoEntity)
                .nota(9.0)
                .build();

        when(alunoJpaRepository.findById(1L)).thenReturn(Optional.of(alunoEntity));
        when(jpaRepository.save(any(AvaliacaoEntity.class))).thenReturn(entitySalva);

        // Act
        Avaliacao resultado = avaliacaoRepository.save(avaliacaoSemId);

        // Assert
        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
        assertEquals(9.0, resultado.getNota());
        verify(jpaRepository, times(1)).save(any(AvaliacaoEntity.class));
    }

    @Test
    @DisplayName("Deve converter corretamente de entity para domain")
    public void deveConverterCorretamenteDeEntityParaDomain() {
        // Arrange
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(avaliacaoEntity));

        // Act
        Optional<Avaliacao> resultado = avaliacaoRepository.findById(1L);

        // Assert
        assertTrue(resultado.isPresent());
        Avaliacao avaliacaoConvertida = resultado.get();
        assertEquals(avaliacaoEntity.getId(), avaliacaoConvertida.getId());
        assertEquals(avaliacaoEntity.getNota(), avaliacaoConvertida.getNota());
        assertEquals(avaliacaoEntity.getAluno().getId(), avaliacaoConvertida.getAluno().getId());
        assertEquals(avaliacaoEntity.getAluno().getNome(), avaliacaoConvertida.getAluno().getNome());
        assertEquals(avaliacaoEntity.getAluno().getEmail(), avaliacaoConvertida.getAluno().getEmail());
    }
}

