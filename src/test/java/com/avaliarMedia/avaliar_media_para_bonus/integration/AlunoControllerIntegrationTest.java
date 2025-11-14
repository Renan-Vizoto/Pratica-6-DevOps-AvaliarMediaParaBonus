package com.avaliarMedia.avaliar_media_para_bonus.integration;

import com.avaliarMedia.avaliar_media_para_bonus.application.dto.AlunoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * Testes de Integração para AlunoController.
 * 
 * @SpringBootTest: Carrega contexto completo da aplicação
 * @AutoConfigureMockMvc: Configura MockMvc para simular requisições HTTP
 * @ActiveProfiles("test"): Usa configurações de teste (H2 em memória)
 * @Transactional: Rollback automático após cada teste
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DisplayName("Testes de Integração - Aluno Controller")
public class AlunoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar um aluno com sucesso")
    public void deveCriarAlunoComSucesso() throws Exception {
        // Arrange
        AlunoDTO alunoDTO = AlunoDTO.builder()
                .nome("João Silva")
                .email("joao.silva@email.com")
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(alunoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nome", is("João Silva")))
                .andExpect(jsonPath("$.email", is("joao.silva@email.com")));
    }

    @Test
    @DisplayName("Deve retornar erro ao criar aluno sem nome")
    public void deveRetornarErroAoCriarAlunoSemNome() throws Exception {
        // Arrange
        AlunoDTO alunoDTO = AlunoDTO.builder()
                .email("joao.silva@email.com")
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(alunoDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar erro ao criar aluno com email inválido")
    public void deveRetornarErroAoCriarAlunoComEmailInvalido() throws Exception {
        // Arrange
        AlunoDTO alunoDTO = AlunoDTO.builder()
                .nome("João Silva")
                .email("email-invalido")
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(alunoDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve buscar aluno por ID com sucesso")
    public void deveBuscarAlunoPorIdComSucesso() throws Exception {
        // Arrange - Criar aluno primeiro
        AlunoDTO alunoDTO = AlunoDTO.builder()
                .nome("Maria Santos")
                .email("maria.santos@email.com")
                .build();

        String response = mockMvc.perform(post("/api/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(alunoDTO)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        AlunoDTO alunoCriado = objectMapper.readValue(response, AlunoDTO.class);

        // Act & Assert
        mockMvc.perform(get("/api/alunos/" + alunoCriado.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(alunoCriado.getId().intValue())))
                .andExpect(jsonPath("$.nome", is("Maria Santos")))
                .andExpect(jsonPath("$.email", is("maria.santos@email.com")));
    }

    @Test
    @DisplayName("Deve buscar aluno por email com sucesso")
    public void deveBuscarAlunoPorEmailComSucesso() throws Exception {
        // Arrange - Criar aluno primeiro
        AlunoDTO alunoDTO = AlunoDTO.builder()
                .nome("Pedro Costa")
                .email("pedro.costa@email.com")
                .build();

        mockMvc.perform(post("/api/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(alunoDTO)))
                .andExpect(status().isCreated());

        // Act & Assert
        mockMvc.perform(get("/api/alunos/email/pedro.costa@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Pedro Costa")))
                .andExpect(jsonPath("$.email", is("pedro.costa@email.com")));
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar aluno inexistente por ID")
    public void deveRetornar404AoBuscarAlunoInexistentePorId() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/alunos/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar aluno inexistente por email")
    public void deveRetornar404AoBuscarAlunoInexistentePorEmail() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/alunos/email/naoexiste@email.com"))
                .andExpect(status().isNotFound());
    }
}

