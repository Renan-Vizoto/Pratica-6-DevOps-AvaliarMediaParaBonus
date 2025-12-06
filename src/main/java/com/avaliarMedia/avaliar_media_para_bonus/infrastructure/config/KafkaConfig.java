package com.avaliarMedia.avaliar_media_para_bonus.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Kafka.
 * Define beans necessários para serialização/deserialização de eventos.
 */
@Configuration
public class KafkaConfig {
    
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}

