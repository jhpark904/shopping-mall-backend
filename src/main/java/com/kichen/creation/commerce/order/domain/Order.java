package com.kichen.creation.commerce.order.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // TODO: 생성시 제약 걸기
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

    public static Order createOrder(List<OrderLine> orderLines) {
        Order order = new Order();

        for (OrderLine orderLine: orderLines) {
            order.orderLineList.add(orderLine);
            orderLine.createOrder(order);
        }
        return order;
    }
}
