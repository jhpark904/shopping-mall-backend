package com.kichen.creation.commerce.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String s) {
        super(s);
    }

    public ProductNotFoundException(String s, Throwable cause) {
        super(s, cause);
    }
}
