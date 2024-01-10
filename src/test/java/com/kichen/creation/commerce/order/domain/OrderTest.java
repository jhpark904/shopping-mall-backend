package com.kichen.creation.commerce.order.domain;

import com.kichen.creation.commerce.order.domain.Order;
import com.kichen.creation.commerce.order.domain.OrderLine;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void addOrderLine() {
        Order order = new Order();
        OrderLine orderLine = new OrderLine();

        order.addOrderLine(orderLine);
        Assertions.assertThat(order.getOrderLineList().size()).isEqualTo(1);
        Assertions.assertThat(order.getOrderLineList()).contains(orderLine);
    }
}