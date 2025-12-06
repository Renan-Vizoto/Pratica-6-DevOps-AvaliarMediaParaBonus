package ac2_project.example.ac2_ca.infrastructure.messaging;

import ac2_project.example.ac2_ca.domain.event.GamificationEvent;
import ac2_project.example.ac2_ca.domain.port.DomainEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessagingDomainEventPublisher implements DomainEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(MessagingDomainEventPublisher.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public MessagingDomainEventPublisher(KafkaTemplate<String, String> kafkaTemplate,
                                         RabbitTemplate rabbitTemplate,
                                         ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(GamificationEvent event) {
        String payload;
        try {
            payload = objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.error("Erro ao serializar evento, enviando toString", e);
            payload = event.toString();
        }

        try {
            ProducerRecord<String, String> record =
                    new ProducerRecord<>("gamification-events",
                            event.getAlunoId().toString(),
                            payload);
            kafkaTemplate.send(record);
            log.info("[KAFKA] Evento enviado");
        } catch (Exception e) {
            log.warn("Falha ao enviar para Kafka: {}", e.getMessage());
        }

        try {
            rabbitTemplate.convertAndSend(
                    MessagingInfrastructureConfig.GAMIFICATION_EXCHANGE,
                    MessagingInfrastructureConfig.GAMIFICATION_ROUTING_KEY,
                    payload
            );
            log.info("[RABBITMQ] Evento enviado");
        } catch (Exception e) {
            log.warn("Falha ao enviar para RabbitMQ: {}", e.getMessage());
        }
    }
}
