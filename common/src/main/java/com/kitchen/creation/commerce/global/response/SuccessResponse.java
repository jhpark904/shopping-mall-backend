package com.kitchen.creation.commerce.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {
    private final int statusCode;
    private final String message;
    private T data;

    public SuccessResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public SuccessResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
