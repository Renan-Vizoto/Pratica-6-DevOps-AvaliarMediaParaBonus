package com.avaliarMedia.avaliar_media_para_bonus.application.service;

import com.avaliarMedia.avaliar_media_para_bonus.application.dto.AvaliacaoDTO;
import com.avaliarMedia.avaliar_media_para_bonus.domain.entity.Aluno;
import com.avaliarMedia.avaliar_media_para_bonus.domain.entity.Avaliacao;
import com.avaliarMedia.avaliar_media_para_bonus.domain.repository.AlunoRepository;
import com.avaliarMedia.avaliar_media_para_bonus.domain.repository.AvaliacaoRepository;
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
 * Testes unitários para AvaliacaoService.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários - AvaliacaoService")
public class AvaliacaoServiceTest {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    private Aluno aluno;
    private Avaliacao avaliacao;

    @BeforeEach
    public void setup() {
        aluno = Aluno.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .build();

        avaliacao = Avaliacao.builder()
                .id(1L)
                .aluno(aluno)
                .nota(8.5)
                .build();
    }

    @Test
    @DisplayName("Deve criar avaliação com sucesso")
    public void deveCriarAvaliacaoComSucesso() {
        // Arrange
        AvaliacaoDTO avaliacaoDTO = AvaliacaoDTO.builder()
                .alunoId(1L)
                .nota(8.5)
                .build();

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(avaliacao);

        // Act
        AvaliacaoDTO resultado = avaliacaoService.criar(avaliacaoDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(8.5, resultado.getNota());
        verify(alunoRepository, times(1)).findById(1L);
        verify(avaliacaoRepository, times(1)).save(any(Avaliacao.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando aluno não encontrado ao criar avaliação")
    public void deveLancarExcecaoQuandoAlunoNaoEncontrado() {
        // Arrange
        AvaliacaoDTO avaliacaoDTO = AvaliacaoDTO.builder()
                .alunoId(999L)
                .nota(8.5)
                .build();

        when(alunoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            avaliacaoService.criar(avaliacaoDTO);
        });

        assertEquals("Aluno não encontrado com ID: 999", exception.getMessage());
        verify(alunoRepository, times(1)).findById(999L);
        verify(avaliacaoRepository, never()).save(any(Avaliacao.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando avaliação não encontrada por ID")
    public void deveLancarExcecaoQuandoAvaliacaoNaoEncontradaPorId() {
        // Arrange
        when(avaliacaoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            avaliacaoService.buscarPorId(999L);
        });

        assertEquals("Avaliação não encontrada com ID: 999", exception.getMessage());
        verify(avaliacaoRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Deve buscar avaliação por aluno ID com sucesso")
    public void deveBuscarAvaliacaoPorAlunoIdComSucesso() {
        // Arrange
        when(avaliacaoRepository.findByAlunoId(1L)).thenReturn(Optional.of(avaliacao));

        // Act
        AvaliacaoDTO resultado = avaliacaoService.buscarPorAlunoId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(8.5, resultado.getNota());
        assertEquals(1L, resultado.getAlunoId());
        verify(avaliacaoRepository, times(1)).findByAlunoId(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando avaliação não encontrada por aluno ID")
    public void deveLancarExcecaoQuandoAvaliacaoNaoEncontradaPorAlunoId() {
        // Arrange
        when(avaliacaoRepository.findByAlunoId(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            avaliacaoService.buscarPorAlunoId(999L);
        });

        assertEquals("Avaliação não encontrada para aluno ID: 999", exception.getMessage());
        verify(avaliacaoRepository, times(1)).findByAlunoId(999L);
    }
}
