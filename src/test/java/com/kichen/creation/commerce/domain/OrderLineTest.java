package com.kichen.creation.commerce.domain;

import org.aspectj.weaver.ast.Or;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderLineTest {

    @Test
    void updaterOrder() {
        OrderLine orderLine = new OrderLine();
        Order order = new Order();

        orderLine.updateOrder(order);
        Assertions.assertThat(orderLine.getOrder()).isEqualTo(order);
    }
}