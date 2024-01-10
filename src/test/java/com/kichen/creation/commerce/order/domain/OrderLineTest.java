package com.kichen.creation.commerce.order.domain;

import com.kichen.creation.commerce.order.domain.Order;
import com.kichen.creation.commerce.order.domain.OrderLine;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderLineTest {

    @Test
    void updaterOrder() {
        OrderLine orderLine = new OrderLine();
        Order order = new Order();

        orderLine.createOrder(order);
        Assertions.assertThat(orderLine.getOrder()).isEqualTo(order);
    }
}