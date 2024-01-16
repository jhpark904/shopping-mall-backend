package com.kichen.creation.commerce.order.domain;

import com.kichen.creation.commerce.product.domain.Product;
import com.kichen.creation.commerce.product.exception.NotEnoughStockException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderTest {

    Product product = mock();
    int testCount = 10;
    OrderLine orderLine = new OrderLine(product, testCount);

    @Test
    void createOrderSuccess() {
        List<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine);
        Order order = Order.createOrder(orderLines);

        assertEquals(order.getOrderLineList(), orderLines);
    }

    @Test
    void createOrderFail() {
        List<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine);
        doThrow(NotEnoughStockException.class).when(product).removeStock(testCount);

        assertThrows(NotEnoughStockException.class, () -> Order.createOrder(orderLines));
    }

    @Test
    void createOrderMultiThreadAccess() {
        int poolSize = 5;
        List<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine);
        CountDownLatch latch = new CountDownLatch(poolSize);

        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        for (int i=0; i<poolSize; i++) {
            executorService.submit(() -> {
                try {
                    Order.createOrder(orderLines);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }

        try { latch.await(); } catch (InterruptedException e) {}
    }
}