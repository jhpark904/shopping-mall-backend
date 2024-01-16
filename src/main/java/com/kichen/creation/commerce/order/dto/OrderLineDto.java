package com.kichen.creation.commerce.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderLineDto {
    @NotNull(message = "Invalid Product Id: productId is Null")
    private final Long productId;

    @Min(value = 1, message = "Invalid Stock: Less than 1")
    private final int count;
}
