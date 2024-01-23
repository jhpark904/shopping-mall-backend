package com.kichen.creation.commerce.order.dto;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderResponseDto {

    private static final float ONE_HUNDRED_FLOAT = 100f;

    private final Long id;

    private final List<OrderLineResponseDto> orderLineList;

    private final LocalDateTime orderDate;

    private final float totalCost;

    public OrderResponseDto(
            @NonNull Long id,
            @NonNull List<OrderLineResponseDto> orderLineList,
            @NonNull LocalDateTime orderDate
    ) {
        validateOrderLineResponseDtoList(orderLineList);

        this.id = id;
        this.orderLineList = orderLineList;
        this.orderDate = orderDate;
        this.totalCost = calculateTotalCost(orderLineList);
    }

    private float calculateTotalCost(List<OrderLineResponseDto> orderLineList) {
        float cost = 0f;
        for (OrderLineResponseDto orderLine: orderLineList) {
            cost += orderLine.getCost();
        }
        return Math.round(cost * ONE_HUNDRED_FLOAT / ONE_HUNDRED_FLOAT);
    }

    private void validateOrderLineResponseDtoList(
            List<OrderLineResponseDto> orderLineResponseDtoList
    ) {
        if (CollectionUtils.isEmpty(orderLineResponseDtoList)) {
            throw new IllegalArgumentException("OrderLineResponseDtoList cannot be null or empty!");
        }
    }
}
