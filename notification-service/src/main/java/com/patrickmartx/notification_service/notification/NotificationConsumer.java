package com.patrickmartx.notification_service.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patrickmartx.notification_service.model.Notification;
import com.patrickmartx.notification_service.repository.NotificationRepository;
import com.patrickmartx.notification_service.dto.OrderMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class NotificationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

    private NotificationRepository repository;
    private ObjectMapper objectMapper;

    public NotificationConsumer(NotificationRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "order-message", groupId = "notification-group")
    public void consume(String message) {
        try {
            OrderMessageDto messageDto = objectMapper.readValue(message, OrderMessageDto.class);

            Notification notification = new Notification();
            notification.setOrderId(messageDto.id());

            Notification savedNotification = repository.save(notification);

            String formatedCreateDate = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm").format(messageDto.createAt().atZoneSameInstant(ZoneId.of("America/Sao_Paulo")));
            String formatedReceivedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm").format(savedNotification.getReceivedAt());

            logger.info("Mensagem recebida com sucesso: id={}, nome do produto={}, quantidade={}, hora do pedido={}, hora do recebimento do pedido={}",
                    messageDto.id(),
                    messageDto.productName(),
                    messageDto.quantity(),
                    formatedCreateDate,
                    formatedReceivedDate
            );

        } catch (JsonProcessingException e) {
            logger.error("Erro ao tentar ler a mensagem JSON: {}", message, e);
        }
    }
}
