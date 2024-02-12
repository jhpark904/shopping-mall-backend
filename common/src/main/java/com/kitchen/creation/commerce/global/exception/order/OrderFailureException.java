package com.kitchen.creation.commerce.global.exception.order;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class OrderFailureException extends RuntimeException {
    @Getter
    private final int httpStatusCode = HttpStatus.BAD_REQUEST.value();

    public OrderFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
