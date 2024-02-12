package com.kitchen.creation.commerce.global.exception.product;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends RuntimeException {
    @Getter
    private final int httpStatusCode = HttpStatus.NOT_FOUND.value();

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
