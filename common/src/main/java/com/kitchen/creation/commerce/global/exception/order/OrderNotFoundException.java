package com.kitchen.creation.commerce.global.exception.order;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends RuntimeException {
    @Getter
    private final int httpStatusCode = HttpStatus.NOT_FOUND.value();

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
