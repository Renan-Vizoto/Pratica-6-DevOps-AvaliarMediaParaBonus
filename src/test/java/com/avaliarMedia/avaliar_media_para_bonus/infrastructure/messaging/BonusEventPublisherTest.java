package com.avaliarMedia.avaliar_media_para_bonus.infrastructure.messaging;

import com.avaliarMedia.avaliar_media_para_bonus.domain.event.BonusConcedidoEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BonusEventPublisherTest {
    
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;
    
    @Mock
    private ObjectMapper objectMapper;
    
    @InjectMocks
    private BonusEventPublisher publisher;
    
    @Test
    void devePublicarEventoNoKafka() throws Exception {
        // Given
        BonusConcedidoEvent event = new BonusConcedidoEvent(1L, "João", 8.5, 3);
        
        when(objectMapper.writeValueAsString(event)).thenReturn("{\"alunoId\":1,\"nomeAluno\":\"João\",\"nota\":8.5,\"quantidadeCursosBonus\":3}");
        
        // When
        publisher.publicarBonusConcedido(event);
        
        // Then
        ArgumentCaptor<ProducerRecord<String, String>> captor = ArgumentCaptor.forClass(ProducerRecord.class);
        verify(kafkaTemplate).send(captor.capture());
        
        ProducerRecord<String, String> record = captor.getValue();
        assertThat(record.topic()).isEqualTo("bonus-concedido-events");
        assertThat(record.key()).isEqualTo("1");
    }
}

