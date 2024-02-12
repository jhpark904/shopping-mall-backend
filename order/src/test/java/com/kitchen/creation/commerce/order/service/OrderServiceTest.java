package com.kitchen.creation.commerce.order.service;

import com.kitchen.creation.commerce.global.exception.order.OrderFailureException;
import com.kitchen.creation.commerce.global.exception.order.OrderNotFoundException;
import com.kitchen.creation.commerce.order.cost.PricingStrategy;
import com.kitchen.creation.commerce.order.cost.SimplePricingStrategy;
import com.kitchen.creation.commerce.order.domain.Order;
import com.kitchen.creation.commerce.order.dto.OrderLineRequestDto;
import com.kitchen.creation.commerce.order.dto.OrderResponseDto;
import com.kitchen.creation.commerce.order.repository.OrderRepository;
import com.kitchen.creation.commerce.product.domain.Product;
import com.kitchen.creation.commerce.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    OrderRepository orderRepository = mock();

    ProductRepository productRepository = mock();

    PricingStrategy pricingStrategy = new SimplePricingStrategy();

    OrderService orderService = new OrderService(orderRepository, productRepository, pricingStrategy);

    Long testId = 0L;
    String testName = "testProduct";

    int testStock = 10;

    Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product(testId, testName, 15f, testStock);
    }

    @Test
    void createOrderSuccess() {
        int count = 3;
        List<OrderLineRequestDto> orderLineRequestDtoList = new ArrayList<>();
        orderLineRequestDtoList.add(new OrderLineRequestDto(testId, count));
        when(productRepository.findById(testId)).thenReturn(Optional.of(testProduct));

        orderService.createOrder(orderLineRequestDtoList);

        Assertions.assertThat(testProduct.getStock()).isEqualTo(testStock - count);
    }

    @Test
    void createOrderFail() {
        int count = 11;
        List<OrderLineRequestDto> orderLineRequestDtoList = new ArrayList<>();
        orderLineRequestDtoList.add(new OrderLineRequestDto(testId, count));
        when(productRepository.findById(testId)).thenReturn(Optional.of(testProduct));

        assertThrows(OrderFailureException.class, () -> orderService.createOrder(orderLineRequestDtoList));
    }

    @Test
    void findOrder() {
        Long testId = 0L;
        Order testOrder = mock();
        OrderResponseDto testOrderResponseDto = mock();
        when(testOrder.toOrderResponseDto()).thenReturn(testOrderResponseDto);
        when(orderRepository.findById(testId)).thenReturn(Optional.of(testOrder));

        orderService.findOrder(testId);

        verify(orderRepository).findById(testId);
    }

    @Test
    void findOrderFail() {
        Long testId = 0L;
        when(orderRepository.findById(testId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.findOrder(testId));
    }

    @Test
    void findAllOrders() {
        orderService.findAllOrders();

        verify(orderRepository).findAll();
    }
}