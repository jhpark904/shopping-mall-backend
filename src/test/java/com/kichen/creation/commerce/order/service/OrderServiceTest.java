package com.kichen.creation.commerce.order.service;

import com.kichen.creation.commerce.order.dto.OrderLineDto;
import com.kichen.creation.commerce.order.repository.OrderRepository;
import com.kichen.creation.commerce.product.domain.Product;
import com.kichen.creation.commerce.product.exception.NotEnoughStockException;
import com.kichen.creation.commerce.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    OrderRepository orderRepository = mock();

    ProductRepository productRepository = mock();

    OrderService orderService = new OrderService(orderRepository, productRepository);

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
        List<OrderLineDto> orderLineDtoList = new ArrayList<>();
        orderLineDtoList.add(new OrderLineDto(testId, count));
        when(productRepository.getReferenceById(testId)).thenReturn(testProduct);

        orderService.createOrder(orderLineDtoList);

        Assertions.assertThat(testProduct.getStock()).isEqualTo(testStock - count);
    }

    @Test
    void createOrderFail() {
        int count = 11;
        List<OrderLineDto> orderLineDtoList = new ArrayList<>();
        orderLineDtoList.add(new OrderLineDto(testId, count));
        when(productRepository.getReferenceById(testId)).thenReturn(testProduct);

        assertThrows(NotEnoughStockException.class, () -> orderService.createOrder(orderLineDtoList));
    }

    @Test
    void findOrder() {
        Long testId = 0L;

        orderService.findOrder(testId);

        verify(orderRepository).getReferenceById(testId);
    }

    @Test
    void findAllOrders() {
        orderService.findAllOrders();

        verify(orderRepository).findAll();
    }
}