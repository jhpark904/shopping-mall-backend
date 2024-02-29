package com.kitchen.creation.commerce.order.dto;

import com.kitchen.creation.commerce.product.dto.ProductResponseDto;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class OrderLineResponseDto {
    private final Long id;

    private final ProductResponseDto product;

    private final int count;

    private final float cost;

    public OrderLineResponseDto(
            @NonNull Long id,
            @NonNull ProductResponseDto product,
            int count,
            float cost
    ) {
        validateCount(count);
        validateCost(cost);

        this.id = id;
        this.product = product;
        this.count = count;
        this.cost = cost;
    }

    private void validateCount(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Count cannot be less than 1!");
        }
    }

    private void validateCost(float cost) {
        if (cost <= 0) {
            throw new IllegalArgumentException("Cost has to be greater than 0!");
        }
    }
}
