package com.kichen.creation.commerce.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // TODO: 양방향 vs 단방향
    private List<OrderLine> orderLineList = new ArrayList<>();

    private boolean successful;

    private LocalDateTime orderDate;

    public void addOrderLine(OrderLine orderLine) {
        orderLineList.add(orderLine);
        orderLine.updateOrder(this);
    }
}