package com.kitchen.creation.commerce;

import com.kitchen.creation.commerce.global.exception.order.OrderFailureException;
import com.kitchen.creation.commerce.order.domain.Order;
import com.kitchen.creation.commerce.order.dto.OrderLineRequestDto;
import com.kitchen.creation.commerce.order.repository.OrderRepository;
import com.kitchen.creation.commerce.order.service.OrderService;
import com.kitchen.creation.commerce.product.domain.Product;
import com.kitchen.creation.commerce.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class CommerceApplicationTests {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    private Product savedProduct;

    private CountDownLatch latch;

    private final AtomicInteger errorCount = new AtomicInteger(0);

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        errorCount.set(0);
    }

    @Test
    void createOrderMultiThreadOneOrderPass() throws InterruptedException {
        int poolSize = 10000;
        int orderAmount = 50;
        int stock = 50;

        // Create a new product and save to db
        Product product = new Product(
                "test product",
                32.0f,
                stock
        );
        savedProduct = productRepository.save(product);

        runOrderCallables(poolSize, orderAmount);

        latch.await();
        Assertions.assertThat(orderRepository.findAll().size()).isEqualTo(1);
        Assertions.assertThat(productRepository.findById(savedProduct.getId()).get().getStock())
                .isEqualTo(0);
        // exception except 1 order
        Assertions.assertThat(errorCount.get()).isEqualTo(
                calculateErrorCount(orderAmount, poolSize, stock)
        );
    }

    @Test
    void createOrderMultiThreadMultipleOrderPass() throws InterruptedException {
        int poolSize = 10;
        int orderAmount = 5;
        int stock = 50;

        // Create a new product and save to db
        Product product = new Product(
                "test product",
                32.0f,
                stock
        );
        savedProduct = productRepository.save(product);

        runOrderCallables(poolSize, orderAmount);

        latch.await();
        Assertions.assertThat(orderRepository.findAll().size()).isEqualTo(poolSize);
        // no exception
        Assertions.assertThat(errorCount.get()).isEqualTo(calculateErrorCount(orderAmount, poolSize, stock));
        Assertions.assertThat(productRepository.findById(savedProduct.getId()).get().getStock())
                .isEqualTo(0);
    }

    private void runOrderCallables(int poolSize, int orderAmount) throws InterruptedException {
        latch = new CountDownLatch(poolSize);

        Collection<CreateOrderCallable> createOrderCallableList = new ArrayList<>();
        for (int i=0; i<poolSize; i++) {
            List<OrderLineRequestDto> orderLineList = new ArrayList<>();
            orderLineList.add(
                    new OrderLineRequestDto(
                            savedProduct.getId(),
                            orderAmount
                    )
            );

            createOrderCallableList.add(new CreateOrderCallable(
                    orderService,
                    orderLineList,
                    latch,
                    errorCount
            ));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        executorService.invokeAll(createOrderCallableList);
    }

    private int calculateErrorCount(int orderAmount, int poolSize, int stock) {
        int errorCount = 0;
        for (int i=0; i<poolSize; i++) {
            stock -= orderAmount;

            if (stock < 0) {
                errorCount += 1;
            }
        }

        return errorCount;
    }

    static class CreateOrderCallable implements Callable<Order> {
        private final CountDownLatch latch;

        private final List<OrderLineRequestDto> orderLineRequestDtos;
        private final OrderService orderService;

        private final AtomicInteger errorCount;


        public CreateOrderCallable(
                OrderService orderService,
                List<OrderLineRequestDto> orderLines,
                CountDownLatch latch,
                AtomicInteger errorCount
        ) {
            this.orderService = orderService;
            this.orderLineRequestDtos = orderLines;
            this.latch = latch;
            this.errorCount = errorCount;
        }

        @Override
        public Order call() {
            try {
                orderService.createOrder(
                        orderLineRequestDtos
                );

            } catch (OrderFailureException e) {
                errorCount.getAndAdd(1);
            } finally {
                latch.countDown();
            }

            return null;
        }
    }
}
