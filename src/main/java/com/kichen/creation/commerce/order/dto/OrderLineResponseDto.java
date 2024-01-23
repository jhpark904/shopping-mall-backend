package com.kichen.creation.commerce.order.dto;

import com.kichen.creation.commerce.product.dto.ProductResponseDto;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class OrderLineResponseDto {
    private static final float ONE_HUNDRED_FLOAT = 100f;

    private final Long id;

    private final ProductResponseDto product;

    private final int count;

    private final float cost;

    public OrderLineResponseDto(
            @NonNull Long id,
            @NonNull ProductResponseDto product,
            int count
    ) {
        validateCount(count);

        this.id = id;
        this.product = product;
        this.count = count;
        this.cost = Math.round(count * product.getPrice() * ONE_HUNDRED_FLOAT) / ONE_HUNDRED_FLOAT;
    }

    private void validateCount(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Count cannot be less than 1!");
        }
    }
}
