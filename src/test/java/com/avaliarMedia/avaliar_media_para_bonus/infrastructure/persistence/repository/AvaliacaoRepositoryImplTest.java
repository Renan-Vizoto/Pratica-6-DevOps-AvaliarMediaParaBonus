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
        verify(alunoJpaRepository, times(1)).findById(1L);
        verify(jpaRepository, times(1)).save(any(AvaliacaoEntity.class));
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
    @DisplayName("Deve lançar exceção quando aluno não encontrado ao salvar avaliação")
    public void deveLancarExcecaoQuandoAlunoNaoEncontradoAoSalvar() {
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
}
