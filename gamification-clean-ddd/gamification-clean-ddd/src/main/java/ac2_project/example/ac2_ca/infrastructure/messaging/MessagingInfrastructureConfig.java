package ac2_project.example.ac2_ca.infrastructure.messaging;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@IntegrationComponentScan
public class MessagingInfrastructureConfig {

    public static final String GAMIFICATION_EXCHANGE = "gamification.exchange";
    public static final String GAMIFICATION_QUEUE = "gamification.queue";
    public static final String GAMIFICATION_ROUTING_KEY = "gamification.event";

    // =========================
    // RABBITMQ (exchange/queue)
    // =========================

    @Bean
    public TopicExchange gamificationExchange() {
        return new TopicExchange(GAMIFICATION_EXCHANGE);
    }

    @Bean
    public Queue gamificationQueue() {
        return new Queue(GAMIFICATION_QUEUE, true);
    }

    @Bean
    public Binding gamificationBinding(Queue gamificationQueue, TopicExchange gamificationExchange) {
        return BindingBuilder.bind(gamificationQueue)
                .to(gamificationExchange)
                .with(GAMIFICATION_ROUTING_KEY);
    }

    // ==========
    // MQTT
    // ==========

    @Bean
    public MqttPahoClientFactory mqttClientFactory(
            @Value("${mqtt.broker-url:tcp://localhost:1883}") String brokerUrl) {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{brokerUrl});
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageHandler mqttOutbound(MqttPahoClientFactory mqttClientFactory,
                                       @Value("${mqtt.client-id:gamification-api}") String clientId) {
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(clientId, mqttClientFactory);
        handler.setAsync(true);
        handler.setDefaultTopic("gamification/default");
        handler.setConverter(new DefaultPahoMessageConverter());
        return handler;
    }

    @Bean
    public IntegrationFlow mqttOutFlow(
            @Qualifier("mqttOutboundChannel") MessageChannel mqttOutboundChannel,
            @Qualifier("mqttOutbound") MessageHandler mqttOutbound) {

        return IntegrationFlow.from(mqttOutboundChannel)
                .handle(mqttOutbound)
                .get();
    }
}
