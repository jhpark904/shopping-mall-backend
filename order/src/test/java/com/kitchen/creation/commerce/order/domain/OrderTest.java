package com.kitchen.creation.commerce.order.domain;

import com.kitchen.creation.commerce.global.exception.order.OrderFailureException;
import com.kitchen.creation.commerce.global.exception.product.NotEnoughStockException;
import com.kitchen.creation.commerce.order.cost.PricingStrategy;
import com.kitchen.creation.commerce.order.cost.SimplePricingStrategy;
import com.kitchen.creation.commerce.product.domain.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

class OrderTest {

    Product product = mock();
    int testCount = 10;
    OrderLine orderLine = new OrderLine(product, testCount);
    PricingStrategy pricingStrategy = new SimplePricingStrategy();

    @Test
    void createOrderSuccess() {
        List<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine);
        Order order = Order.createOrder(orderLines, pricingStrategy);

        assertEquals(order.getOrderLineList(), orderLines);
    }

    @Test
    void createOrderFail() {
        List<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine);
        doThrow(NotEnoughStockException.class).when(product).removeStock(testCount);

        assertThrows(OrderFailureException.class, () -> Order.createOrder(orderLines, pricingStrategy));
    }
}