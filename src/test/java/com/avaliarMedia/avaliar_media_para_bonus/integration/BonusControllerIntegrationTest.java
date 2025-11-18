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
 * Testes de Integração para BonusController.
 * Testa o fluxo completo de cálculo de bônus baseado na nota do aluno.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DisplayName("Testes de Integração - Bonus Controller")
public class BonusControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long alunoComNotaAltaId;
    private Long alunoComNotaBaixaId;

    @BeforeEach
    public void setup() throws Exception {
        // Criar aluno com nota alta (> 7.0)
        AlunoDTO aluno1 = AlunoDTO.builder()
                .nome("Aluno Aprovado")
                .email("aprovado@email.com")
                .build();

        String response1 = mockMvc.perform(post("/api/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aluno1)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        AlunoDTO alunoCriado1 = objectMapper.readValue(response1, AlunoDTO.class);
        this.alunoComNotaAltaId = alunoCriado1.getId();

        // Criar avaliação com nota alta
        AvaliacaoDTO avaliacao1 = AvaliacaoDTO.builder()
                .alunoId(alunoComNotaAltaId)
                .nota(8.5)
                .build();

        mockMvc.perform(post("/api/avaliacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avaliacao1)))
                .andExpect(status().isCreated());

        // Criar aluno com nota baixa (<= 7.0)
        AlunoDTO aluno2 = AlunoDTO.builder()
                .nome("Aluno Reprovado")
                .email("reprovado@email.com")
                .build();

        String response2 = mockMvc.perform(post("/api/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aluno2)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        AlunoDTO alunoCriado2 = objectMapper.readValue(response2, AlunoDTO.class);
        this.alunoComNotaBaixaId = alunoCriado2.getId();

        // Criar avaliação com nota baixa
        AvaliacaoDTO avaliacao2 = AvaliacaoDTO.builder()
                .alunoId(alunoComNotaBaixaId)
                .nota(5.0)
                .build();

        mockMvc.perform(post("/api/avaliacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avaliacao2)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve calcular 3 cursos bônus para aluno com nota 8.5 (> 7.0)")
    public void deveCalcular3CursosBonusParaAlunoComNotaAlta() throws Exception {
        // Act & Assert - Nota 8.5 > 7.0 = 3 cursos bônus
        mockMvc.perform(get("/api/bonus/aluno/" + alunoComNotaAltaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alunoId", is(alunoComNotaAltaId.intValue())))
                .andExpect(jsonPath("$.nomeAluno", is("Aluno Aprovado")))
                .andExpect(jsonPath("$.nota", is(8.5)))
                .andExpect(jsonPath("$.elegivel", is(true)))
                .andExpect(jsonPath("$.quantidadeCursosBonus", is(3)))
                .andExpect(jsonPath("$.descricao", containsString("Parabéns")))
                .andExpect(jsonPath("$.descricao", containsString("3 cursos bônus")));
    }

    @Test
    @DisplayName("Deve calcular 0 cursos bônus para aluno com nota 5.0 (<= 7.0)")
    public void deveCalcular0CursosBonusParaAlunoComNotaBaixa() throws Exception {
        // Act & Assert - Nota 5.0 <= 7.0 = 0 cursos bônus
        mockMvc.perform(get("/api/bonus/aluno/" + alunoComNotaBaixaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alunoId", is(alunoComNotaBaixaId.intValue())))
                .andExpect(jsonPath("$.nomeAluno", is("Aluno Reprovado")))
                .andExpect(jsonPath("$.nota", is(5.0)))
                .andExpect(jsonPath("$.elegivel", is(false)))
                .andExpect(jsonPath("$.quantidadeCursosBonus", is(0)))
                .andExpect(jsonPath("$.descricao", containsString("não é suficiente")));
    }

    @Test
    @DisplayName("Deve calcular 0 bônus para aluno com nota exatamente 7.0")
    public void deveCalcular0BonusParaAlunoComNotaExatamente7() throws Exception {
        // Arrange - Criar aluno com nota exatamente 7.0
        AlunoDTO aluno = AlunoDTO.builder()
                .nome("Aluno Limite")
                .email("limite@email.com")
                .build();

        String response = mockMvc.perform(post("/api/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aluno)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        AlunoDTO alunoCriado = objectMapper.readValue(response, AlunoDTO.class);

        AvaliacaoDTO avaliacao = AvaliacaoDTO.builder()
                .alunoId(alunoCriado.getId())
                .nota(7.0)
                .build();

        mockMvc.perform(post("/api/avaliacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avaliacao)))
                .andExpect(status().isCreated());

        // Act & Assert - Nota = 7.0 não deve gerar bônus (regra: > 7.0)
        mockMvc.perform(get("/api/bonus/aluno/" + alunoCriado.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nota", is(7.0)))
                .andExpect(jsonPath("$.elegivel", is(false)))
                .andExpect(jsonPath("$.quantidadeCursosBonus", is(0)));
    }
}
