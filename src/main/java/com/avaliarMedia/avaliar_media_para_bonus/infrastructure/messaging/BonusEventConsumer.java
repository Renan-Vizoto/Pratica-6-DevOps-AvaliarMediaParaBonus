package com.avaliarMedia.avaliar_media_para_bonus.infrastructure.messaging;

import com.avaliarMedia.avaliar_media_para_bonus.domain.event.BonusConcedidoEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consumer de eventos do Kafka.
 * Exemplo de microserviço que consome eventos de bônus concedido.
 * 
 * Este consumer representa um possível serviço de Analytics que processa
 * eventos para gerar dashboards e relatórios.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BonusEventConsumer {
    
    private final ObjectMapper objectMapper;
    
    @KafkaListener(topics = "bonus-concedido-events", groupId = "bonus-analytics-group")
    public void consumirBonusConcedido(ConsumerRecord<String, String> record) {
        try {
            log.info("[KAFKA] Mensagem recebida - Key: {}, Partition: {}, Offset: {}", 
                    record.key(), record.partition(), record.offset());
            
            BonusConcedidoEvent event = objectMapper.readValue(record.value(), BonusConcedidoEvent.class);
            
            // Processa o evento (exemplo: salvar em data lake, atualizar dashboard, etc.)
            processarEvento(event);
            
        } catch (JsonProcessingException e) {
            log.error("Erro ao deserializar evento de bônus", e);
        } catch (Exception e) {
            log.error("Erro ao processar evento de bônus", e);
        }
    }
    
    private void processarEvento(BonusConcedidoEvent event) {
        log.info("[ANALYTICS] Processando evento de bônus: alunoId={}, nome={}, nota={}, cursosBonus={}", 
                event.getAlunoId(), event.getNomeAluno(), event.getNota(), event.getQuantidadeCursosBonus());
        
        // Aqui poderia:
        // - Salvar em banco de dados de analytics
        // - Atualizar métricas em tempo real
        // - Enviar para data lake
        // - Gerar notificações
        // - etc.
        
        log.info("[ANALYTICS] Evento processado com sucesso para aluno {}", event.getAlunoId());
    }
}

