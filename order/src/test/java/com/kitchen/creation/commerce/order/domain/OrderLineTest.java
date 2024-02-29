package com.kitchen.creation.commerce.order.domain;

import com.kitchen.creation.commerce.global.exception.order.OrderFailureException;
import com.kitchen.creation.commerce.global.exception.product.NotEnoughStockException;
import com.kitchen.creation.commerce.product.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderLineTest {

    Product product = mock();

    Order order = mock();

    int testCount = 10;

    @Test
    void createOrderSuccess() {
        OrderLine orderLine = new OrderLine(product, testCount);
        orderLine.createOrder(order);
        Assertions.assertThat(orderLine.getOrder()).isEqualTo(order);
        verify(product).removeStock(testCount);
    }

    @Test
    void createOrderFail() {
        OrderLine orderLine = new OrderLine(product, testCount);
        doThrow(NotEnoughStockException.class).when(product).removeStock(testCount);

        assertThrows(OrderFailureException.class, () -> orderLine.createOrder(order));
    }
}
