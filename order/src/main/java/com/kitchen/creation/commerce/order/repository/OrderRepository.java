package com.kitchen.creation.commerce.order.repository;

import com.kitchen.creation.commerce.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
