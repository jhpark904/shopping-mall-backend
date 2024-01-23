package com.kichen.creation.commerce.order.domain;

import com.kichen.creation.commerce.order.dto.OrderResponseDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    public static Order create(List<OrderLine> orderLines) {
        Order order = new Order();

        for (OrderLine orderLine: orderLines) {
            order.orderLineList.add(orderLine);
            orderLine.createOrder(order);
        }
        return order;
    }

    public OrderResponseDto toOrderResponseDto() {
        return new OrderResponseDto(
                id,
                orderLineList.stream().map(OrderLine::toOrderLineResponseDto).toList(),
                orderDate
        );
    }
}
