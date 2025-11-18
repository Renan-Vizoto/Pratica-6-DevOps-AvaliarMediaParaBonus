package com.avaliarMedia.avaliar_media_para_bonus.application.service;

import com.avaliarMedia.avaliar_media_para_bonus.application.dto.BonusResponseDTO;
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
import static org.mockito.Mockito.*;

/**
 * Testes unitários para BonusService.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários - BonusService")
public class BonusServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @InjectMocks
    private BonusService bonusService;

    private Aluno aluno;
    private Avaliacao avaliacaoComNotaAlta;
    private Avaliacao avaliacaoComNotaBaixa;

    @BeforeEach
    public void setup() {
        aluno = Aluno.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .build();

        avaliacaoComNotaAlta = Avaliacao.builder()
                .id(1L)
                .aluno(aluno)
                .nota(8.5)
                .build();

        avaliacaoComNotaBaixa = Avaliacao.builder()
                .id(2L)
                .aluno(aluno)
                .nota(5.0)
                .build();
    }

    @Test
    @DisplayName("Deve calcular 3 cursos bônus para nota maior que 7.0")
    public void deveCalcular3CursosBonusParaNotaMaiorQue7() {
        // Arrange
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(avaliacaoRepository.findByAlunoId(1L)).thenReturn(Optional.of(avaliacaoComNotaAlta));

        // Act
        BonusResponseDTO resultado = bonusService.calcularBonus(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getAlunoId());
        assertEquals("João Silva", resultado.getNomeAluno());
        assertEquals(8.5, resultado.getNota());
        assertTrue(resultado.getElegivel());
        assertEquals(3, resultado.getQuantidadeCursosBonus());
        assertTrue(resultado.getDescricao().contains("Parabéns"));
        assertTrue(resultado.getDescricao().contains("3 cursos bônus"));
        
        verify(alunoRepository, times(1)).findById(1L);
        verify(avaliacaoRepository, times(1)).findByAlunoId(1L);
    }

    @Test
    @DisplayName("Deve calcular 0 cursos bônus para nota menor ou igual a 7.0")
    public void deveCalcular0CursosBonusParaNotaMenorOuIgual7() {
        // Arrange
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(avaliacaoRepository.findByAlunoId(1L)).thenReturn(Optional.of(avaliacaoComNotaBaixa));

        // Act
        BonusResponseDTO resultado = bonusService.calcularBonus(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getAlunoId());
        assertEquals("João Silva", resultado.getNomeAluno());
        assertEquals(5.0, resultado.getNota());
        assertFalse(resultado.getElegivel());
        assertEquals(0, resultado.getQuantidadeCursosBonus());
        assertTrue(resultado.getDescricao().contains("não é suficiente"));
        
        verify(alunoRepository, times(1)).findById(1L);
        verify(avaliacaoRepository, times(1)).findByAlunoId(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando aluno não encontrado")
    public void deveLancarExcecaoQuandoAlunoNaoEncontrado() {
        // Arrange
        when(alunoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bonusService.calcularBonus(999L);
        });

        assertEquals("Aluno não encontrado com ID: 999", exception.getMessage());
        verify(alunoRepository, times(1)).findById(999L);
        verify(avaliacaoRepository, never()).findByAlunoId(anyLong());
    }

    @Test
    @DisplayName("Deve lançar exceção quando avaliação não encontrada")
    public void deveLancarExcecaoQuandoAvaliacaoNaoEncontrada() {
        // Arrange
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(avaliacaoRepository.findByAlunoId(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bonusService.calcularBonus(1L);
        });

        assertEquals("Avaliação não encontrada para aluno ID: 1", exception.getMessage());
        verify(alunoRepository, times(1)).findById(1L);
        verify(avaliacaoRepository, times(1)).findByAlunoId(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando nota é null")
    public void deveLancarExcecaoQuandoNotaNull() {
        // Arrange
        Avaliacao avaliacaoSemNota = Avaliacao.builder()
                .id(1L)
                .aluno(aluno)
                .nota(null)
                .build();

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(avaliacaoRepository.findByAlunoId(1L)).thenReturn(Optional.of(avaliacaoSemNota));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bonusService.calcularBonus(1L);
        });

        assertEquals("Nota não encontrada para aluno ID: 1", exception.getMessage());
        verify(alunoRepository, times(1)).findById(1L);
        verify(avaliacaoRepository, times(1)).findByAlunoId(1L);
    }

    @Test
    @DisplayName("Deve calcular bônus para nota exatamente 7.0")
    public void deveCalcularBonusParaNotaExatamente7() {
        // Arrange
        Avaliacao avaliacaoNota7 = Avaliacao.builder()
                .id(1L)
                .aluno(aluno)
                .nota(7.0)
                .build();

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(avaliacaoRepository.findByAlunoId(1L)).thenReturn(Optional.of(avaliacaoNota7));

        // Act
        BonusResponseDTO resultado = bonusService.calcularBonus(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(7.0, resultado.getNota());
        assertFalse(resultado.getElegivel());
        assertEquals(0, resultado.getQuantidadeCursosBonus());
    }

    @Test
    @DisplayName("Deve calcular bônus para nota 7.1")
    public void deveCalcularBonusParaNota71() {
        // Arrange
        Avaliacao avaliacaoNota71 = Avaliacao.builder()
                .id(1L)
                .aluno(aluno)
                .nota(7.1)
                .build();

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(avaliacaoRepository.findByAlunoId(1L)).thenReturn(Optional.of(avaliacaoNota71));

        // Act
        BonusResponseDTO resultado = bonusService.calcularBonus(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(7.1, resultado.getNota());
        assertTrue(resultado.getElegivel());
        assertEquals(3, resultado.getQuantidadeCursosBonus());
    }

    @Test
    @DisplayName("Deve gerar descrição correta para bônus elegível")
    public void deveGerarDescricaoCorretaParaBonusElegivel() {
        // Arrange
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(avaliacaoRepository.findByAlunoId(1L)).thenReturn(Optional.of(avaliacaoComNotaAlta));

        // Act
        BonusResponseDTO resultado = bonusService.calcularBonus(1L);

        // Assert
        String descricao = resultado.getDescricao();
        assertTrue(descricao.contains("Parabéns"));
        assertTrue(descricao.contains("maior que 7.0"));
        assertTrue(descricao.contains("3 cursos bônus"));
    }

    @Test
    @DisplayName("Deve gerar descrição correta para bônus não elegível")
    public void deveGerarDescricaoCorretaParaBonusNaoElegivel() {
        // Arrange
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(avaliacaoRepository.findByAlunoId(1L)).thenReturn(Optional.of(avaliacaoComNotaBaixa));

        // Act
        BonusResponseDTO resultado = bonusService.calcularBonus(1L);

        // Assert
        String descricao = resultado.getDescricao();
        assertTrue(descricao.contains("não é suficiente"));
        assertTrue(descricao.contains("superior a 7.0"));
    }
}

