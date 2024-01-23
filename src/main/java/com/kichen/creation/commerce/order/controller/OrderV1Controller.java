package com.kichen.creation.commerce.order.controller;

import com.kichen.creation.commerce.global.response.SuccessResponse;
import com.kichen.creation.commerce.order.dto.OrderRequestDto;
import com.kichen.creation.commerce.order.dto.OrderResponseDto;
import com.kichen.creation.commerce.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderV1Controller {

    private final OrderService orderService;

    @GetMapping("/v1/orders")
    public SuccessResponse<List<OrderResponseDto>> getAllOrders() {
        return new SuccessResponse<>(HttpStatus.OK.value(), "Success", orderService.findAllOrders());
    }

    @GetMapping("/v1/orders/{id}")
    public SuccessResponse<OrderResponseDto> getOrder(@PathVariable Long id) {
        return new SuccessResponse<>(HttpStatus.OK.value(), "Success", orderService.findOrder(id));
    }

    @PostMapping("/v1/orders")
    public SuccessResponse<?> createOrder(@RequestBody OrderRequestDto order) {
        orderService.createOrder(order.getOrderLineRequestDtoList());
        return new SuccessResponse<>(HttpStatus.OK.value(), "Success");
    }
}
