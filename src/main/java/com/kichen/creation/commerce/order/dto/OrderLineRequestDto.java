package com.kichen.creation.commerce.order.dto;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class OrderLineRequestDto {
    private final Long productId;

    private final int count;

    public OrderLineRequestDto(
            @NonNull Long productId,
            int count
    ) {
        validateCount(count);

        this.productId = productId;
        this.count = count;
    }

    private void validateCount(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Count cannot be less than 1!");
        }
    }
}
