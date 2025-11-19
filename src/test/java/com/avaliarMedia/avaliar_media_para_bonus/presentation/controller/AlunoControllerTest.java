package com.avaliarMedia.avaliar_media_para_bonus.presentation.controller;

import com.avaliarMedia.avaliar_media_para_bonus.application.dto.AlunoDTO;
import com.avaliarMedia.avaliar_media_para_bonus.application.service.AlunoService;
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
 * Testes Unitários para AlunoController usando @WebMvcTest.
 */
@WebMvcTest(AlunoController.class)
@DisplayName("Testes Unitários - AlunoController")
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AlunoService alunoService;

    @Test
    @DisplayName("Deve criar aluno com sucesso - Status 201")
    public void deveCriarAlunoComSucesso() throws Exception {
        // Arrange
        AlunoDTO alunoDTO = AlunoDTO.builder()
                .nome("João Silva")
                .email("joao@email.com")
                .build();

        AlunoDTO alunoCriado = AlunoDTO.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .build();

        when(alunoService.criar(any(AlunoDTO.class))).thenReturn(alunoCriado);

        // Act & Assert
        mockMvc.perform(post("/api/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(alunoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva"));

        verify(alunoService, times(1)).criar(any(AlunoDTO.class));
    }

    @Test
    @DisplayName("Deve buscar aluno por ID com sucesso - Status 200")
    public void deveBuscarAlunoPorIdComSucesso() throws Exception {
        // Arrange
        AlunoDTO alunoDTO = AlunoDTO.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .build();

        when(alunoService.buscarPorId(1L)).thenReturn(alunoDTO);

        // Act & Assert
        mockMvc.perform(get("/api/alunos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva"));

        verify(alunoService, times(1)).buscarPorId(1L);
    }

    @Test
    @DisplayName("Deve buscar aluno por email com sucesso - Status 200")
    public void deveBuscarAlunoPorEmailComSucesso() throws Exception {
        // Arrange
        AlunoDTO alunoDTO = AlunoDTO.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .build();

        when(alunoService.buscarPorEmail("joao@email.com")).thenReturn(alunoDTO);

        // Act & Assert
        mockMvc.perform(get("/api/alunos/email/joao@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));

        verify(alunoService, times(1)).buscarPorEmail("joao@email.com");
    }
}
