package ac2_project.example.ac2_ca.infrastructure.ai;

import ac2_project.example.ac2_ca.domain.model.Aluno;
import ac2_project.example.ac2_ca.domain.model.PlanoAssinatura;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LangChainTutorServiceTest {

    @Mock
    ChatLanguageModel model;

    @InjectMocks
    LangChainTutorService service;

    @Test
    void deveGerarSugestaoUsandoModelo() {
        Aluno aluno = new Aluno(1L, "Andreia", PlanoAssinatura.BASIC);
        when(model.generate(anyString())).thenReturn("Sugestão mockada de cursos");

        String resposta = service.sugerirProximosCursos(aluno);

        assertThat(resposta).isEqualTo("Sugestão mockada de cursos");
    }
}
