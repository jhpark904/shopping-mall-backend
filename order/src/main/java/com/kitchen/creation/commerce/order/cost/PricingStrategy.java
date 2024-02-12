package com.kitchen.creation.commerce.order.cost;

import com.kitchen.creation.commerce.order.domain.OrderLine;
import lombok.NonNull;

import java.util.List;

public interface PricingStrategy {
    float calculatePrice(
            @NonNull List<OrderLine> orderLines
    );
}
