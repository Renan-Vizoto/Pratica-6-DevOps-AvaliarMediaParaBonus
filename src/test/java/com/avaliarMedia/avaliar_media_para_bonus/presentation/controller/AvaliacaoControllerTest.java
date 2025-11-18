package com.avaliarMedia.avaliar_media_para_bonus.presentation.controller;

import com.avaliarMedia.avaliar_media_para_bonus.application.dto.AvaliacaoDTO;
import com.avaliarMedia.avaliar_media_para_bonus.application.service.AvaliacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes Unitários para AvaliacaoController usando @WebMvcTest.
 * 
 * VANTAGENS DO @WebMvcTest:
 * - Testa apenas a camada web (controller) sem carregar todo o contexto Spring
 * - Mocka automaticamente os services (@MockBean)
 * - Execução muito mais rápida que testes de integração
 * - Isolamento: falhas em service/repository não afetam este teste
 */
@WebMvcTest(AvaliacaoController.class)
@DisplayName("Testes Unitários - AvaliacaoController")
public class AvaliacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AvaliacaoService avaliacaoService;

    @Test
    @DisplayName("Deve criar avaliação com sucesso - Status 201")
    public void deveCriarAvaliacaoComSucesso() throws Exception {
        // Arrange
        AvaliacaoDTO avaliacaoDTO = AvaliacaoDTO.builder()
                .alunoId(1L)
                .nota(8.5)
                .build();

        AvaliacaoDTO avaliacaoCriada = AvaliacaoDTO.builder()
                .id(1L)
                .alunoId(1L)
                .nota(8.5)
                .build();

        when(avaliacaoService.criar(any(AvaliacaoDTO.class))).thenReturn(avaliacaoCriada);

        // Act & Assert
        mockMvc.perform(post("/api/avaliacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avaliacaoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.alunoId").value(1L))
                .andExpect(jsonPath("$.nota").value(8.5));

        verify(avaliacaoService, times(1)).criar(any(AvaliacaoDTO.class));
    }

    @Test
    @DisplayName("Deve retornar erro 400 ao criar avaliação sem alunoId")
    public void deveRetornarErro400AoCriarAvaliacaoSemAlunoId() throws Exception {
        // Arrange
        AvaliacaoDTO avaliacaoDTO = AvaliacaoDTO.builder()
                .nota(8.5)
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/avaliacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avaliacaoDTO)))
                .andExpect(status().isBadRequest());

        verify(avaliacaoService, never()).criar(any(AvaliacaoDTO.class));
    }

    @Test
    @DisplayName("Deve retornar erro 400 ao criar avaliação sem nota")
    public void deveRetornarErro400AoCriarAvaliacaoSemNota() throws Exception {
        // Arrange
        AvaliacaoDTO avaliacaoDTO = AvaliacaoDTO.builder()
                .alunoId(1L)
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/avaliacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avaliacaoDTO)))
                .andExpect(status().isBadRequest());

        verify(avaliacaoService, never()).criar(any(AvaliacaoDTO.class));
    }

    @Test
    @DisplayName("Deve buscar avaliação por ID com sucesso - Status 200")
    public void deveBuscarAvaliacaoPorIdComSucesso() throws Exception {
        // Arrange
        AvaliacaoDTO avaliacaoDTO = AvaliacaoDTO.builder()
                .id(1L)
                .alunoId(1L)
                .nota(8.5)
                .build();

        when(avaliacaoService.buscarPorId(1L)).thenReturn(avaliacaoDTO);

        // Act & Assert
        mockMvc.perform(get("/api/avaliacoes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.alunoId").value(1L))
                .andExpect(jsonPath("$.nota").value(8.5));

        verify(avaliacaoService, times(1)).buscarPorId(1L);
    }

    @Test
    @DisplayName("Deve buscar avaliação por aluno ID com sucesso - Status 200")
    public void deveBuscarAvaliacaoPorAlunoIdComSucesso() throws Exception {
        // Arrange
        AvaliacaoDTO avaliacaoDTO = AvaliacaoDTO.builder()
                .id(1L)
                .alunoId(1L)
                .nota(8.5)
                .build();

        when(avaliacaoService.buscarPorAlunoId(1L)).thenReturn(avaliacaoDTO);

        // Act & Assert
        mockMvc.perform(get("/api/avaliacoes/aluno/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.alunoId").value(1L))
                .andExpect(jsonPath("$.nota").value(8.5));

        verify(avaliacaoService, times(1)).buscarPorAlunoId(1L);
    }

    @Test
    @DisplayName("Deve retornar erro 500 quando service lança exceção")
    public void deveRetornarErro500QuandoServiceLancaExcecao() throws Exception {
        // Arrange
        when(avaliacaoService.buscarPorId(999L))
                .thenThrow(new RuntimeException("Avaliação não encontrada com ID: 999"));

        // Act & Assert
        mockMvc.perform(get("/api/avaliacoes/999"))
                .andExpect(status().isInternalServerError());

        verify(avaliacaoService, times(1)).buscarPorId(999L);
    }

    @Test
    @DisplayName("Deve validar mapeamento correto do endpoint")
    public void deveValidarMapeamentoCorretoDoEndpoint() throws Exception {
        // Arrange
        AvaliacaoDTO avaliacaoDTO = AvaliacaoDTO.builder()
                .id(1L)
                .alunoId(1L)
                .nota(8.5)
                .build();

        when(avaliacaoService.buscarPorId(1L)).thenReturn(avaliacaoDTO);

        // Act & Assert - Verifica se o endpoint está mapeado corretamente
        mockMvc.perform(get("/api/avaliacoes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}

