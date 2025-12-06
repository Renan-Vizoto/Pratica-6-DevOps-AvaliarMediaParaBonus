package ac2_project.example.ac2_ca.interfaceadapters.rest;

import ac2_project.example.ac2_ca.application.dto.GamificationRequest;
import ac2_project.example.ac2_ca.application.dto.GamificationResponse;
import ac2_project.example.ac2_ca.application.usecase.RegistrarConclusaoCursoUseCase;
import ac2_project.example.ac2_ca.domain.model.Aluno;
import ac2_project.example.ac2_ca.domain.model.PlanoAssinatura;
import ac2_project.example.ac2_ca.domain.port.AlunoRepository;
import ac2_project.example.ac2_ca.infrastructure.ai.LangChainTutorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GamificationController.class)
class GamificationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    RegistrarConclusaoCursoUseCase registrarConclusaoCursoUseCase;

    @MockBean
    AlunoRepository alunoRepository;

    @MockBean
    LangChainTutorService tutorService;

    @Test
    void deveRegistrarConclusaoViaPost() throws Exception {
        GamificationRequest request = new GamificationRequest();
        request.setAlunoId(1L);
        request.setNomeCurso("Curso de Teste");
        request.setMediaFinal(9.0);
        request.setAjudouOutros(true);

        given(registrarConclusaoCursoUseCase.execute(any()))
                .willReturn(new GamificationResponse(1L, 3, 2, "PREMIUM"));

        mockMvc.perform(post("/api/gamification/courses/completion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alunoId").value(1L))
                .andExpect(jsonPath("$.cursosDisponiveis").value(3))
                .andExpect(jsonPath("$.moedas").value(2))
                .andExpect(jsonPath("$.plano").value("PREMIUM"));
    }

    @Test
    void deveRetornarRecomendacoesDaIA() throws Exception {
        Aluno aluno = new Aluno(1L, "Aluno Demo", PlanoAssinatura.BASIC);
        given(alunoRepository.findById(1L)).willReturn(Optional.of(aluno));
        given(tutorService.sugerirProximosCursos(any()))
                .willReturn("Sugestão de cursos da IA");

        mockMvc.perform(get("/api/gamification/1/ai/recommendations"))
                .andExpect(status().isOk())
                .andExpect(content().string("Sugestão de cursos da IA"));
    }
}
