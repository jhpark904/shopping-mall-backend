package com.kitchen.creation.commerce.global.exception;

import com.kitchen.creation.commerce.order.exception.OrderFailureException;
import com.kitchen.creation.commerce.order.exception.OrderNotFoundException;
import com.kitchen.creation.commerce.product.exception.ProductNotFoundException;
import com.kitchen.creation.commerce.global.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getMessage(),
                        LocalDateTime.now()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage(),
                        LocalDateTime.now()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {

        return ResponseEntity
                .status(ex.getHttpStatusCode())
                .body(new ErrorResponse(
                        ex.getHttpStatusCode(),
                        ex.getMessage(),
                        LocalDateTime.now()));
    }

    @ExceptionHandler(OrderFailureException.class)
    public ResponseEntity<ErrorResponse> handleOrderFailureException(OrderFailureException ex) {

        return ResponseEntity
                .status(ex.getHttpStatusCode())
                .body(new ErrorResponse(
                        ex.getHttpStatusCode(),
                        ex.getMessage(),
                        LocalDateTime.now()));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException ex) {

        return ResponseEntity
                .status(ex.getHttpStatusCode())
                .body(new ErrorResponse(
                        ex.getHttpStatusCode(),
                        ex.getMessage(),
                        LocalDateTime.now()));
    }
}