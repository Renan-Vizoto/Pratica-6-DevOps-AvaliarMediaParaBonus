package ac2_project.example.ac2_ca.infrastructure.messaging;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface GamificationMqttGateway {

    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, String payload);
}
