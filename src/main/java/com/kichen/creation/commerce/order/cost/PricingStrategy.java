package com.kichen.creation.commerce.order.cost;

import com.kichen.creation.commerce.order.domain.OrderLine;
import lombok.NonNull;

import java.util.List;

public interface PricingStrategy {
    float calculatePrice(
            @NonNull List<OrderLine> orderLines
    );
}
