package com.kichen.creation.commerce.order.domain;

import com.kichen.creation.commerce.order.exception.OrderFailureException;
import com.kichen.creation.commerce.product.domain.Product;
import com.kichen.creation.commerce.product.exception.NotEnoughStockException;
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
