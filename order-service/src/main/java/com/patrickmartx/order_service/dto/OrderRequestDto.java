package com.patrickmartx.order_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderRequestDto(@NotNull String productName, @Min(value = 0) Integer quantity) {
}
