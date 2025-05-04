package com.patrickmartx.order_service.message;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderMessageProducer {
    private static final String TOPIC = "order-message";

    private KafkaTemplate<String, String> kafkaTemplate;

    public OrderMessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
