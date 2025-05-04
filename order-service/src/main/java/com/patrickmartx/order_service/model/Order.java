package com.patrickmartx.order_service.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "order_timestamp", nullable = false)
    private OffsetDateTime createAt = OffsetDateTime.now(ZoneId.of("America/Sao_Paulo"));

    public UUID getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public OffsetDateTime getCreateAt() {
        return createAt;
    }
}
