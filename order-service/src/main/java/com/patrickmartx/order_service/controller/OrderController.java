package com.patrickmartx.order_service.controller;

import com.patrickmartx.order_service.dto.OrderRequestDto;
import com.patrickmartx.order_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<String> createOrder(@RequestBody @Valid OrderRequestDto requestDto) {
        return ResponseEntity.ok().body(service.createOrder(requestDto));
    }
}
