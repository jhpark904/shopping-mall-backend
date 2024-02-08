package com.kitchen.creation.commerce.order.dto;

import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OrderRequestDto {
    private List<OrderLineRequestDto> orderLineRequestDtoList;

    public OrderRequestDto(
            @NonNull List<OrderLineRequestDto> orderLineRequestDtoList
    ) {
        validateOrderLineRequestDtoList(orderLineRequestDtoList);

        this.orderLineRequestDtoList = orderLineRequestDtoList;
    }

    private void validateOrderLineRequestDtoList(
            List<OrderLineRequestDto> orderLineRequestDtoList
    ) {
        if (CollectionUtils.isEmpty(orderLineRequestDtoList)) {
            throw new IllegalArgumentException("OrderLineRequestDtoList cannot be null or empty!");
        }
    }
}
