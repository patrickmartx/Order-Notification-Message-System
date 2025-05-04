package com.patrickmartx.order_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patrickmartx.order_service.dto.OrderRequestDto;
import com.patrickmartx.order_service.exception.MessageConversionException;
import com.patrickmartx.order_service.message.OrderMessageProducer;
import com.patrickmartx.order_service.model.Order;
import com.patrickmartx.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderService {

    OrderRepository repository;
    OrderMessageProducer messageProducer;
    ObjectMapper objectMapper;

    public OrderService(OrderRepository repository, OrderMessageProducer messageProducer, ObjectMapper objectMapper) {
        this.repository = repository;
        this.messageProducer = messageProducer;
        this.objectMapper = objectMapper;
    }

    public String createOrder(OrderRequestDto requestDto) {
        Order order = new Order();
        order.setProductName(requestDto.productName());
        order.setQuantity(requestDto.quantity());

        Order savedOrder = repository.save(order);

        sendOrderMessage(savedOrder);

        return """
                Seu pedido foi enviado.
                Data de envio: %s
                Id do pedido: %s
                """.formatted(formatDate(savedOrder.getCreateAt()), savedOrder.getId());
    }

    private void sendOrderMessage(Order savedOrder) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(savedOrder);
            messageProducer.sendOrderMessage(jsonMessage);
        } catch (JsonProcessingException e) {
            throw new MessageConversionException(String.format("Falha ao converter mensagem do pedido ID: %s para JSON", savedOrder.getId()),
                    e
            );
        }
    }

    private String formatDate(OffsetDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        return dateTime.format(formatter);
    }
}
