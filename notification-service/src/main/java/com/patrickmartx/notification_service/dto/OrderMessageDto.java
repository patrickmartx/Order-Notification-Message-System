package com.patrickmartx.notification_service.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderMessageDto(
        UUID id,
        String productName,
        Integer quantity,
        OffsetDateTime createAt
) {
}
