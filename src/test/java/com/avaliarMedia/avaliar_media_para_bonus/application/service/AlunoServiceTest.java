package com.avaliarMedia.avaliar_media_para_bonus.application.service;

import com.avaliarMedia.avaliar_media_para_bonus.application.dto.AlunoDTO;
import com.avaliarMedia.avaliar_media_para_bonus.domain.entity.Aluno;
import com.avaliarMedia.avaliar_media_para_bonus.domain.repository.AlunoRepository;
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
 * Testes unitários para AlunoService.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários - AlunoService")
public class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    private Aluno aluno;
    private AlunoDTO alunoDTO;

    @BeforeEach
    public void setup() {
        aluno = Aluno.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .build();

        alunoDTO = AlunoDTO.builder()
                .nome("João Silva")
                .email("joao@email.com")
                .build();
    }

    @Test
    @DisplayName("Deve criar aluno com sucesso")
    public void deveCriarAlunoComSucesso() {
        // Arrange
        when(alunoRepository.save(any(Aluno.class))).thenReturn(aluno);

        // Act
        AlunoDTO resultado = alunoService.criar(alunoDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@email.com", resultado.getEmail());
        
        verify(alunoRepository, times(1)).save(any(Aluno.class));
    }

    @Test
    @DisplayName("Deve buscar aluno por ID com sucesso")
    public void deveBuscarAlunoPorIdComSucesso() {
        // Arrange
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        // Act
        AlunoDTO resultado = alunoService.buscarPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@email.com", resultado.getEmail());
        
        verify(alunoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar aluno inexistente por ID")
    public void deveLancarExcecaoAoBuscarAlunoInexistentePorId() {
        // Arrange
        when(alunoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            alunoService.buscarPorId(999L);
        });

        assertEquals("Aluno não encontrado com ID: 999", exception.getMessage());
        verify(alunoRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Deve buscar aluno por email com sucesso")
    public void deveBuscarAlunoPorEmailComSucesso() {
        // Arrange
        when(alunoRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(aluno));

        // Act
        AlunoDTO resultado = alunoService.buscarPorEmail("joao@email.com");

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@email.com", resultado.getEmail());
        
        verify(alunoRepository, times(1)).findByEmail("joao@email.com");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar aluno inexistente por email")
    public void deveLancarExcecaoAoBuscarAlunoInexistentePorEmail() {
        // Arrange
        when(alunoRepository.findByEmail("naoexiste@email.com")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            alunoService.buscarPorEmail("naoexiste@email.com");
        });

        assertEquals("Aluno não encontrado com email: naoexiste@email.com", exception.getMessage());
        verify(alunoRepository, times(1)).findByEmail("naoexiste@email.com");
    }

    @Test
    @DisplayName("Deve converter corretamente de domain para DTO")
    public void deveConverterCorretamenteDeDomainParaDTO() {
        // Arrange
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        // Act
        AlunoDTO resultado = alunoService.buscarPorId(1L);

        // Assert
        assertEquals(aluno.getId(), resultado.getId());
        assertEquals(aluno.getNome(), resultado.getNome());
        assertEquals(aluno.getEmail(), resultado.getEmail());
    }

    @Test
    @DisplayName("Deve criar aluno com diferentes dados")
    public void deveCriarAlunoComDiferentesDados() {
        // Arrange
        AlunoDTO novoAlunoDTO = AlunoDTO.builder()
                .nome("Maria Santos")
                .email("maria@email.com")
                .build();

        Aluno novoAluno = Aluno.builder()
                .id(2L)
                .nome("Maria Santos")
                .email("maria@email.com")
                .build();

        when(alunoRepository.save(any(Aluno.class))).thenReturn(novoAluno);

        // Act
        AlunoDTO resultado = alunoService.criar(novoAlunoDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
        assertEquals("Maria Santos", resultado.getNome());
        assertEquals("maria@email.com", resultado.getEmail());
    }
}

