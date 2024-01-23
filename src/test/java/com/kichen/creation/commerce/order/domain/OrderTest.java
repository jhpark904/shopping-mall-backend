package com.kichen.creation.commerce.order.domain;

import com.kichen.creation.commerce.order.exception.OrderFailureException;
import com.kichen.creation.commerce.product.domain.Product;
import com.kichen.creation.commerce.product.domain.TestProduct;
import com.kichen.creation.commerce.product.exception.NotEnoughStockException;
import org.assertj.core.api.Assertions;
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
        Order order = Order.create(orderLines);

        assertEquals(order.getOrderLineList(), orderLines);
    }

    @Test
    void createOrderFail() {
        List<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine);
        doThrow(NotEnoughStockException.class).when(product).removeStock(testCount);

        assertThrows(OrderFailureException.class, () -> Order.create(orderLines));
    }

    @Test
    void createOrderMultiThreadAccess() throws InterruptedException {
        int poolSize = 10000;
        List<OrderLine> orderLines = new ArrayList<>();
        Product testProduct = new TestProduct("test", 10f, 10);
        OrderLine testOrderLine = new OrderLine(testProduct, testCount);
        orderLines.add(testOrderLine);

        CountDownLatch latch = new CountDownLatch(poolSize);
        CountDownLatch latch2 = new CountDownLatch(poolSize);

        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        for (int i=0; i<poolSize; i++) {
            executorService.submit(() -> {
                try {
                    latch.await();
                    Order.create(orderLines);
                } catch (Exception e) {}

                latch2.countDown();
            });

            latch.countDown();
        }

        latch2.await();
        Assertions.assertThat(testProduct.getStock()).isLessThan(0);
    }
}