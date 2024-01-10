package com.kichen.creation.commerce.order.domain;

import com.kichen.creation.commerce.product.domain.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_line_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @Getter
    private Order order;

    private int count;

    private float totalPrice;

    public OrderLine(Product product, Order order, int count, float totalPrice) {
        this.product = product;
        this.order = order;
        this.count = count;
        this.totalPrice = totalPrice;
    }

    public void createOrder(Order order) {
        this.order = order;
    }
}
