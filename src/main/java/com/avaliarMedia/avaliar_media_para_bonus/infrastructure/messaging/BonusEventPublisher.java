package com.avaliarMedia.avaliar_media_para_bonus.infrastructure.messaging;

import com.avaliarMedia.avaliar_media_para_bonus.domain.event.BonusConcedidoEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Publisher de eventos para Kafka.
 * Publica eventos de bônus concedido no tópico Kafka.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BonusEventPublisher {
    
    private static final String TOPIC_BONUS_EVENTS = "bonus-concedido-events";
    
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    
    /**
     * Publica um evento de bônus concedido no Kafka.
     * 
     * @param event Evento de bônus concedido
     */
    public void publicarBonusConcedido(BonusConcedidoEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            
            ProducerRecord<String, String> record = new ProducerRecord<>(
                    TOPIC_BONUS_EVENTS,
                    event.getAlunoId().toString(), // Key: ID do aluno (garante ordem por aluno)
                    payload
            );
            
            kafkaTemplate.send(record);
            log.info("[KAFKA] Evento de bônus publicado: alunoId={}, cursosBonus={}", 
                    event.getAlunoId(), event.getQuantidadeCursosBonus());
            
        } catch (JsonProcessingException e) {
            log.error("Erro ao serializar evento de bônus", e);
        } catch (Exception e) {
            log.warn("Falha ao publicar evento no Kafka: {}", e.getMessage());
        }
    }
}

