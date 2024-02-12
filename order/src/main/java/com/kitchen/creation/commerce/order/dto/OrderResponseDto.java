package com.kitchen.creation.commerce.order.dto;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderResponseDto {

    private final Long id;

    private final List<OrderLineResponseDto> orderLineList;

    private final LocalDateTime orderDate;

    private final float totalCost;

    public OrderResponseDto(
            @NonNull Long id,
            @NonNull List<OrderLineResponseDto> orderLineList,
            @NonNull LocalDateTime orderDate,
            float totalCost
    ) {
        validateOrderLineResponseDtoList(orderLineList);

        this.id = id;
        this.orderLineList = orderLineList;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
    }

    private void validateOrderLineResponseDtoList(
            List<OrderLineResponseDto> orderLineResponseDtoList
    ) {
        if (CollectionUtils.isEmpty(orderLineResponseDtoList)) {
            throw new IllegalArgumentException("OrderLineResponseDtoList cannot be null or empty!");
        }
    }
}
