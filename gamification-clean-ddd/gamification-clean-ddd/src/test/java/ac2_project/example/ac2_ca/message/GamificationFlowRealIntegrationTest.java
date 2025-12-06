package ac2_project.example.ac2_ca.message;

import ac2_project.example.ac2_ca.domain.model.Aluno;
import ac2_project.example.ac2_ca.domain.model.PlanoAssinatura;
import ac2_project.example.ac2_ca.infrastructure.persistence.InMemoryAlunoRepository;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GamificationFlowRealIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InMemoryAlunoRepository alunoRepository;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private ChatLanguageModel chatLanguageModel;

    private final Long alunoId = 1L;

    @BeforeEach
    void setup() {
        // limpa o "banco" em memória e recria o aluno do cenário
        alunoRepository.clear();

        Aluno aluno = new Aluno(alunoId, "Aluno Teste", PlanoAssinatura.BASIC);
        alunoRepository.save(aluno);
    }

    @Test
    void fluxoCompleto_devePublicarMensagensEmKafkaERabbit() throws Exception {

        String requestJson = """
            {
              "alunoId": 1,
              "nomeCurso": "Curso Real",
              "mediaFinal": 8.7,
              "ajudouOutros": true
            }
            """;

        // 1) chama o endpoint real
        var mvcResult = mockMvc.perform(
                    post("/api/gamification/courses/completion")
                            .contentType("application/json")
                            .content(requestJson)
                )
                .andExpect(status().isOk())
                // corpo real: {"alunoId":1,"cursosDisponiveis":3,"moedas":2,"plano":"BASIC"}
                .andExpect(jsonPath("$.alunoId").value(1))
                .andExpect(jsonPath("$.cursosDisponiveis").value(3))
                .andExpect(jsonPath("$.moedas").value(2))
                .andExpect(jsonPath("$.plano").value("BASIC"))
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        System.out.println("RESPOSTA HTTP: " + responseBody);
        assertThat(responseBody).isNotBlank();

        // 2) Verifica que ALGUMA mensagem foi enviada para o Kafka
        //    A aplicação usa send(ProducerRecord...), então usamos any(ProducerRecord.class)
        verify(kafkaTemplate,
                timeout(Duration.ofSeconds(5).toMillis()))
                .send(any(ProducerRecord.class));

        // 3) Verifica que ALGUMA mensagem foi enviada para o RabbitMQ
        verify(rabbitTemplate,
                timeout(Duration.ofSeconds(5).toMillis()))
                .convertAndSend(
                        anyString(),         // exchange
                        anyString(),         // routing key
                        any(Object.class)    // payload (Object) para evitar ambiguidade
                );

        System.out.println("\n✔✔ FLUXO COMPLETO COM MOCKS OK (HTTP + Kafka + Rabbit) ✔✔\n");
    }
}

