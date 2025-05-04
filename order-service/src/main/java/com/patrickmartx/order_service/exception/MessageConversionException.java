package com.patrickmartx.order_service.exception;

public class MessageConversionException extends RuntimeException {
    public MessageConversionException(String message, Throwable cause) {
        super(message);
    }
}
