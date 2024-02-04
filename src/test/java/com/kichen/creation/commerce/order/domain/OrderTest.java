package com.kichen.creation.commerce.order.domain;

import com.kichen.creation.commerce.order.cost.PricingStrategy;
import com.kichen.creation.commerce.order.cost.SimplePricingStrategy;
import com.kichen.creation.commerce.order.exception.OrderFailureException;
import com.kichen.creation.commerce.product.domain.Product;
import com.kichen.creation.commerce.product.exception.NotEnoughStockException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderTest {

    Product product = mock();
    int testCount = 10;
    OrderLine orderLine = new OrderLine(product, testCount);
    PricingStrategy pricingStrategy = new SimplePricingStrategy();

    @Test
    void createOrderSuccess() {
        List<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine);
        Order order = new Order(orderLines, pricingStrategy);

        assertEquals(order.getOrderLineList(), orderLines);
    }

    @Test
    void createOrderFail() {
        List<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine);
        doThrow(NotEnoughStockException.class).when(product).removeStock(testCount);

        assertThrows(OrderFailureException.class, () -> new Order(orderLines, pricingStrategy));
    }
}