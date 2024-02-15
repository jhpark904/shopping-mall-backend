package com.kitchen.creation.commerce.order.service;

import com.kitchen.creation.commerce.global.exception.order.OrderNotFoundException;
import com.kitchen.creation.commerce.global.exception.product.ProductNotFoundException;
import com.kitchen.creation.commerce.redis.DistributedLock;
import com.kitchen.creation.commerce.order.cost.PricingStrategy;
import com.kitchen.creation.commerce.order.domain.Order;
import com.kitchen.creation.commerce.order.domain.OrderLine;
import com.kitchen.creation.commerce.order.dto.OrderLineRequestDto;
import com.kitchen.creation.commerce.order.dto.OrderResponseDto;
import com.kitchen.creation.commerce.order.repository.OrderRepository;
import com.kitchen.creation.commerce.product.repository.ProductRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PricingStrategy pricingStrategy;

    @DistributedLock(key = "T(com.kitchen.creation.commerce.order.service.OrderService).generateLockKey(#orderLineRequestDtoList)")
    public void createOrder(@NonNull List<OrderLineRequestDto> orderLineRequestDtoList) {
        Order order = Order.createOrder(
                orderLineRequestDtoList.stream().map(this::convertToOrderLine).toList(),
                pricingStrategy
        );

        orderRepository.save(order);
    }

    public OrderResponseDto findOrder(@NonNull Long id) {
        return orderRepository.findById(id).map(
                Order::toOrderResponseDto
        ).orElseThrow(
                () -> new OrderNotFoundException(
                        String.format("Order with (id: %s) is not found!", id)
                )
        );
    }

    public List<OrderResponseDto> findAllOrders() {
        return orderRepository.findAll().stream().map(Order::toOrderResponseDto).toList();
    }

    private OrderLine convertToOrderLine(OrderLineRequestDto orderLineRequestDto) {
        Long productId = orderLineRequestDto.getProductId();
        return productRepository.findById(productId).map(
                (product) -> new OrderLine(product, orderLineRequestDto.getCount())
        ).orElseThrow(
                () -> new ProductNotFoundException(
                        String.format("Order cannot be created because product with (id: %s) does not exist!", productId)
                )
        );
    }

    public static String generateLockKey(@NonNull List<OrderLineRequestDto> orderLineRequestDtoList) {
        if (orderLineRequestDtoList.isEmpty()) {
            throw new IllegalArgumentException("orderLineRequestDtoList cannot be empty");
        }

        return StringUtils.collectionToCommaDelimitedString(
                orderLineRequestDtoList.stream().map(OrderLineRequestDto::getProductId).toList()
        );
    }
}
