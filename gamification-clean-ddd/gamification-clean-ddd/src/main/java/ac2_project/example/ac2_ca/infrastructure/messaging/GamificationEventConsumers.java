package ac2_project.example.ac2_ca.infrastructure.messaging;

import ac2_project.example.ac2_ca.domain.event.GamificationEvent;
import ac2_project.example.ac2_ca.infrastructure.blockchain.BlockchainTokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class GamificationEventConsumers {

    private static final Logger log = LoggerFactory.getLogger(GamificationEventConsumers.class);

    private final BlockchainTokenService blockchainTokenService;
    private final GamificationMqttGateway mqttGateway;
    private final ObjectMapper objectMapper;

    public GamificationEventConsumers(BlockchainTokenService blockchainTokenService,
                                      GamificationMqttGateway mqttGateway,
                                      ObjectMapper objectMapper) {
        this.blockchainTokenService = blockchainTokenService;
        this.mqttGateway = mqttGateway;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "gamification-events", groupId = "gamification-consumers")
    public void onKafkaMessage(ConsumerRecord<String, String> record) throws JsonProcessingException {
        log.info("[KAFKA] Mensagem recebida: {}", record.value());
        process(record.value(), "kafka");
    }

    @RabbitListener(queues = MessagingInfrastructureConfig.GAMIFICATION_QUEUE)
    public void onRabbitMessage(String payload) throws JsonProcessingException {
        log.info("[RABBITMQ] Mensagem recebida: {}", payload);
        process(payload, "rabbitmq");
    }

    private void process(String payload, String source) throws JsonProcessingException {
        GamificationEvent event = objectMapper.readValue(payload, GamificationEvent.class);

        if (event.getMoedas() > 0) {
            blockchainTokenService.registrarMoedasEmBlockchain(
                    event.getAlunoId(),
                    event.getMoedas()
            );
        }

        String topic = "gamification/alunos/" + event.getAlunoId();
        String summary = String.format(
                "[%s] Aluno %d: cursos=%d, moedas=%d, plano=%s",
                source.toUpperCase(),
                event.getAlunoId(),
                event.getCursosDisponiveis(),
                event.getMoedas(),
                event.getPlanoAssinatura()
        );

        mqttGateway.sendToMqtt(topic, summary);
        log.info("[MQTT] Notificação enviada: {}", summary);
    }
}
