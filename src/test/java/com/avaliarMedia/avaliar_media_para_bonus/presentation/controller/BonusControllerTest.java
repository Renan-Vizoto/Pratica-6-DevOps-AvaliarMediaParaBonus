package com.avaliarMedia.avaliar_media_para_bonus.presentation.controller;

import com.avaliarMedia.avaliar_media_para_bonus.application.dto.BonusResponseDTO;
import com.avaliarMedia.avaliar_media_para_bonus.application.service.BonusService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes Unitários para BonusController usando @WebMvcTest.
 * 
 * DIFERENÇA ENTRE TESTE UNITÁRIO E INTEGRAÇÃO:
 * 
 * Teste Unitário (@WebMvcTest):
 * - Testa apenas o controller isoladamente
 * - Mocka o service (não executa lógica real)
 * - Foco: mapeamento HTTP, serialização JSON, status codes
 * - Tempo: ~50ms por teste
 * 
 * Teste de Integração (@SpringBootTest):
 * - Testa controller + service + repository + banco
 * - Executa lógica real de negócio
 * - Foco: fluxo completo end-to-end
 * - Tempo: ~500ms por teste
 */
@WebMvcTest(BonusController.class)
@DisplayName("Testes Unitários - BonusController")
public class BonusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BonusService bonusService;

    @Test
    @DisplayName("Deve calcular bônus com sucesso - Status 200")
    public void deveCalcularBonusComSucesso() throws Exception {
        // Arrange
        BonusResponseDTO bonusDTO = BonusResponseDTO.builder()
                .alunoId(1L)
                .nomeAluno("João Silva")
                .nota(8.5)
                .elegivel(true)
                .quantidadeCursosBonus(3)
                .descricao("Parabéns! Você recebeu 3 cursos bônus.")
                .build();

        when(bonusService.calcularBonus(1L)).thenReturn(bonusDTO);

        // Act & Assert
        mockMvc.perform(get("/api/bonus/aluno/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alunoId").value(1L))
                .andExpect(jsonPath("$.nomeAluno").value("João Silva"))
                .andExpect(jsonPath("$.nota").value(8.5))
                .andExpect(jsonPath("$.elegivel").value(true))
                .andExpect(jsonPath("$.quantidadeCursosBonus").value(3))
                .andExpect(jsonPath("$.descricao").value("Parabéns! Você recebeu 3 cursos bônus."));

        verify(bonusService, times(1)).calcularBonus(1L);
    }

    @Test
    @DisplayName("Deve retornar bônus não elegível - Status 200")
    public void deveRetornarBonusNaoElegivel() throws Exception {
        // Arrange
        BonusResponseDTO bonusDTO = BonusResponseDTO.builder()
                .alunoId(1L)
                .nomeAluno("João Silva")
                .nota(5.0)
                .elegivel(false)
                .quantidadeCursosBonus(0)
                .descricao("Sua nota não é suficiente para receber bônus.")
                .build();

        when(bonusService.calcularBonus(1L)).thenReturn(bonusDTO);

        // Act & Assert
        mockMvc.perform(get("/api/bonus/aluno/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.elegivel").value(false))
                .andExpect(jsonPath("$.quantidadeCursosBonus").value(0));

        verify(bonusService, times(1)).calcularBonus(1L);
    }

    @Test
    @DisplayName("Deve retornar erro 500 quando service lança exceção")
    public void deveRetornarErro500QuandoServiceLancaExcecao() throws Exception {
        // Arrange
        when(bonusService.calcularBonus(999L))
                .thenThrow(new RuntimeException("Aluno não encontrado com ID: 999"));

        // Act & Assert
        mockMvc.perform(get("/api/bonus/aluno/999"))
                .andExpect(status().isInternalServerError());

        verify(bonusService, times(1)).calcularBonus(999L);
    }

    @Test
    @DisplayName("Deve validar Content-Type JSON na resposta")
    public void deveValidarContentTypeJSONNaResposta() throws Exception {
        // Arrange
        BonusResponseDTO bonusDTO = BonusResponseDTO.builder()
                .alunoId(1L)
                .nomeAluno("João Silva")
                .nota(8.5)
                .elegivel(true)
                .quantidadeCursosBonus(3)
                .descricao("Parabéns!")
                .build();

        when(bonusService.calcularBonus(1L)).thenReturn(bonusDTO);

        // Act & Assert
        mockMvc.perform(get("/api/bonus/aluno/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve validar mapeamento correto do endpoint")
    public void deveValidarMapeamentoCorretoDoEndpoint() throws Exception {
        // Arrange
        BonusResponseDTO bonusDTO = BonusResponseDTO.builder()
                .alunoId(1L)
                .nomeAluno("João Silva")
                .nota(8.5)
                .elegivel(true)
                .quantidadeCursosBonus(3)
                .descricao("Parabéns!")
                .build();

        when(bonusService.calcularBonus(1L)).thenReturn(bonusDTO);

        // Act & Assert - Verifica se o endpoint /api/bonus/aluno/{id} está correto
        mockMvc.perform(get("/api/bonus/aluno/1"))
                .andExpect(status().isOk());
    }
}

