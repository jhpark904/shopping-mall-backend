package com.kitchen.creation.commerce.order.domain;

import com.kitchen.creation.commerce.order.cost.PricingStrategy;
import com.kitchen.creation.commerce.order.dto.OrderResponseDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Getter
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> orderLineList = new ArrayList<>();

    @CreatedDate
    private LocalDateTime orderDate;

    private float totalCost;

    public static Order createOrder(
            @NonNull List<OrderLine> orderLines,
            @NonNull PricingStrategy pricingStrategy
    ) {
        Order order = new Order();
        order.validateOrderLineList(orderLines);

        for (OrderLine orderLine: orderLines) {
            order.orderLineList.add(orderLine);
            orderLine.createOrder(order);
        }

        order.totalCost = pricingStrategy.calculatePrice(orderLines);
        return order;
    }

    public OrderResponseDto toOrderResponseDto() {
        return new OrderResponseDto(
                id,
                orderLineList.stream().map(OrderLine::toOrderLineResponseDto).toList(),
                orderDate,
                totalCost
        );
    }

    private void validateOrderLineList(
            List<OrderLine> orderLines
    ) {
        if (CollectionUtils.isEmpty(orderLines)) {
            throw new IllegalArgumentException("OrderLineResponseDtoList cannot be null or empty!");
        }
    }
}
