package com.patrickmartx.notification_service.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Entity(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "received_at", nullable = false)
    private OffsetDateTime receivedAt = OffsetDateTime.now(ZoneId.of("America/Sao_Paulo"));
    @Column(name = "order_id")
    private UUID orderId;

    public UUID getId() {
        return id;
    }

    public OffsetDateTime getReceivedAt() {
        return receivedAt;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
}
