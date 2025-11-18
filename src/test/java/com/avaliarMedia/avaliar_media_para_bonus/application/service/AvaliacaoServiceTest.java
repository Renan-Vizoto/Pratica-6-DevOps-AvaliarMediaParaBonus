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
    private AvaliacaoDTO avaliacaoDTO;

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

        avaliacaoDTO = AvaliacaoDTO.builder()
                .alunoId(1L)
                .nota(8.5)
                .build();
    }

    @Test
    @DisplayName("Deve criar avaliação com sucesso")
    public void deveCriarAvaliacaoComSucesso() {
        // Arrange
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(avaliacao);

        // Act
        AvaliacaoDTO resultado = avaliacaoService.criar(avaliacaoDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(1L, resultado.getAlunoId());
        assertEquals(8.5, resultado.getNota());
        
        verify(alunoRepository, times(1)).findById(1L);
        verify(avaliacaoRepository, times(1)).save(any(Avaliacao.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar avaliação com aluno não encontrado")
    public void deveLancarExcecaoAoCriarAvaliacaoComAlunoNaoEncontrado() {
        // Arrange
        AvaliacaoDTO dtoComAlunoInexistente = AvaliacaoDTO.builder()
                .alunoId(999L)
                .nota(8.5)
                .build();

        when(alunoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            avaliacaoService.criar(dtoComAlunoInexistente);
        });

        assertEquals("Aluno não encontrado com ID: 999", exception.getMessage());
        verify(alunoRepository, times(1)).findById(999L);
        verify(avaliacaoRepository, never()).save(any(Avaliacao.class));
    }

    @Test
    @DisplayName("Deve buscar avaliação por ID com sucesso")
    public void deveBuscarAvaliacaoPorIdComSucesso() {
        // Arrange
        when(avaliacaoRepository.findById(1L)).thenReturn(Optional.of(avaliacao));

        // Act
        AvaliacaoDTO resultado = avaliacaoService.buscarPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(1L, resultado.getAlunoId());
        assertEquals(8.5, resultado.getNota());
        
        verify(avaliacaoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar avaliação inexistente por ID")
    public void deveLancarExcecaoAoBuscarAvaliacaoInexistentePorId() {
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
        assertEquals(1L, resultado.getAlunoId());
        assertEquals(8.5, resultado.getNota());
        
        verify(avaliacaoRepository, times(1)).findByAlunoId(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar avaliação inexistente por aluno ID")
    public void deveLancarExcecaoAoBuscarAvaliacaoInexistentePorAlunoId() {
        // Arrange
        when(avaliacaoRepository.findByAlunoId(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            avaliacaoService.buscarPorAlunoId(999L);
        });

        assertEquals("Avaliação não encontrada para aluno ID: 999", exception.getMessage());
        verify(avaliacaoRepository, times(1)).findByAlunoId(999L);
    }

    @Test
    @DisplayName("Deve criar avaliação com nota baixa")
    public void deveCriarAvaliacaoComNotaBaixa() {
        // Arrange
        AvaliacaoDTO dtoNotaBaixa = AvaliacaoDTO.builder()
                .alunoId(1L)
                .nota(5.0)
                .build();

        Avaliacao avaliacaoNotaBaixa = Avaliacao.builder()
                .id(2L)
                .aluno(aluno)
                .nota(5.0)
                .build();

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(avaliacaoNotaBaixa);

        // Act
        AvaliacaoDTO resultado = avaliacaoService.criar(dtoNotaBaixa);

        // Assert
        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
        assertEquals(5.0, resultado.getNota());
        
        verify(alunoRepository, times(1)).findById(1L);
        verify(avaliacaoRepository, times(1)).save(any(Avaliacao.class));
    }

    @Test
    @DisplayName("Deve converter corretamente de domain para DTO")
    public void deveConverterCorretamenteDeDomainParaDTO() {
        // Arrange
        when(avaliacaoRepository.findById(1L)).thenReturn(Optional.of(avaliacao));

        // Act
        AvaliacaoDTO resultado = avaliacaoService.buscarPorId(1L);

        // Assert
        assertEquals(avaliacao.getId(), resultado.getId());
        assertEquals(avaliacao.getAluno().getId(), resultado.getAlunoId());
        assertEquals(avaliacao.getNota(), resultado.getNota());
    }

    @Test
    @DisplayName("Deve criar avaliação sem ID no DTO")
    public void deveCriarAvaliacaoSemIdNoDTO() {
        // Arrange
        AvaliacaoDTO dtoSemId = AvaliacaoDTO.builder()
                .alunoId(1L)
                .nota(9.0)
                .build();

        Avaliacao avaliacaoSalva = Avaliacao.builder()
                .id(3L)
                .aluno(aluno)
                .nota(9.0)
                .build();

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(avaliacaoSalva);

        // Act
        AvaliacaoDTO resultado = avaliacaoService.criar(dtoSemId);

        // Assert
        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals(9.0, resultado.getNota());
    }
}

