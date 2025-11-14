package com.avaliarMedia.avaliar_media_para_bonus.integration;

import com.avaliarMedia.avaliar_media_para_bonus.application.dto.AlunoDTO;
import com.avaliarMedia.avaliar_media_para_bonus.application.dto.AvaliacaoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Testes de Integração para AvaliacaoController.
 * Testa o fluxo completo de criação e consulta de avaliações.
 * 
 * Regra: O aluno tem 1 nota de 1 curso.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DisplayName("Testes de Integração - Avaliação Controller")
public class AvaliacaoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long alunoId;

    @BeforeEach
    public void setup() throws Exception {
        // Criar um aluno para usar nos testes
        AlunoDTO alunoDTO = AlunoDTO.builder()
                .nome("Aluno Teste")
                .email("aluno.teste@email.com")
                .build();

        String response = mockMvc.perform(post("/api/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(alunoDTO)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        AlunoDTO alunoCriado = objectMapper.readValue(response, AlunoDTO.class);
        this.alunoId = alunoCriado.getId();
    }

    @Test
    @DisplayName("Deve criar avaliação com nota alta (8.5)")
    public void deveCriarAvaliacaoComNotaAlta() throws Exception {
        // Arrange
        AvaliacaoDTO avaliacaoDTO = AvaliacaoDTO.builder()
                .alunoId(alunoId)
                .nota(8.5)
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/avaliacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avaliacaoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.alunoId", is(alunoId.intValue())))
                .andExpect(jsonPath("$.nota", is(8.5)));
    }

    @Test
    @DisplayName("Deve criar avaliação com nota baixa (5.0)")
    public void deveCriarAvaliacaoComNotaBaixa() throws Exception {
        // Arrange
        AvaliacaoDTO avaliacaoDTO = AvaliacaoDTO.builder()
                .alunoId(alunoId)
                .nota(5.0)
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/avaliacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avaliacaoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nota", is(5.0)));
    }

    @Test
    @DisplayName("Deve retornar erro ao criar avaliação sem aluno ID")
    public void deveRetornarErroAoCriarAvaliacaoSemAlunoId() throws Exception {
        // Arrange
        AvaliacaoDTO avaliacaoDTO = AvaliacaoDTO.builder()
                .nota(8.0)
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/avaliacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avaliacaoDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar erro ao criar avaliação sem nota")
    public void deveRetornarErroAoCriarAvaliacaoSemNota() throws Exception {
        // Arrange
        AvaliacaoDTO avaliacaoDTO = AvaliacaoDTO.builder()
                .alunoId(alunoId)
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/avaliacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avaliacaoDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve buscar avaliação por ID com sucesso")
    public void deveBuscarAvaliacaoPorIdComSucesso() throws Exception {
        // Arrange - Criar avaliação primeiro
        AvaliacaoDTO avaliacaoDTO = AvaliacaoDTO.builder()
                .alunoId(alunoId)
                .nota(7.5)
                .build();

        String response = mockMvc.perform(post("/api/avaliacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avaliacaoDTO)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        AvaliacaoDTO avaliacaoCriada = objectMapper.readValue(response, AvaliacaoDTO.class);

        // Act & Assert
        mockMvc.perform(get("/api/avaliacoes/" + avaliacaoCriada.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(avaliacaoCriada.getId().intValue())))
                .andExpect(jsonPath("$.alunoId", is(alunoId.intValue())))
                .andExpect(jsonPath("$.nota", is(7.5)));
    }

    @Test
    @DisplayName("Deve buscar avaliação por aluno ID com sucesso")
    public void deveBuscarAvaliacaoPorAlunoIdComSucesso() throws Exception {
        // Arrange - Criar avaliação primeiro
        AvaliacaoDTO avaliacaoDTO = AvaliacaoDTO.builder()
                .alunoId(alunoId)
                .nota(9.0)
                .build();

        mockMvc.perform(post("/api/avaliacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avaliacaoDTO)))
                .andExpect(status().isCreated());

        // Act & Assert
        mockMvc.perform(get("/api/avaliacoes/aluno/" + alunoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alunoId", is(alunoId.intValue())))
                .andExpect(jsonPath("$.nota", is(9.0)));
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar avaliação inexistente por ID")
    public void deveRetornar404AoBuscarAvaliacaoInexistentePorId() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/avaliacoes/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar avaliação de aluno sem avaliação")
    public void deveRetornar404AoBuscarAvaliacaoDeAlunoSemAvaliacao() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/avaliacoes/aluno/999999"))
                .andExpect(status().isNotFound());
    }
}
