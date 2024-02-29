package com.kitchen.creation.commerce.global.exception.product;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(String message) {
        super(message);
    }
}
