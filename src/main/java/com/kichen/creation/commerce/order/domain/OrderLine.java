package com.kichen.creation.commerce.order.domain;

import com.kichen.creation.commerce.order.dto.OrderLineResponseDto;
import com.kichen.creation.commerce.order.exception.OrderFailureException;
import com.kichen.creation.commerce.product.domain.Product;
import com.kichen.creation.commerce.product.exception.NotEnoughStockException;
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

    public OrderLine(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    public void createOrder(Order order) {
        this.order = order;
        try {
            product.removeStock(count);
        } catch (NotEnoughStockException e) {
            throw new OrderFailureException("Order failed!", e);
        }
    }

    public OrderLineResponseDto toOrderLineResponseDto() {
        return new OrderLineResponseDto(
                id,
                product.toProductResponseDto(),
                count
        );
    }
}
