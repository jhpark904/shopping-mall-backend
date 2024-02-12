package com.kitchen.creation.commerce.order.cost;

import com.kitchen.creation.commerce.order.domain.OrderLine;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimplePricingStrategy implements PricingStrategy {
    @Override
    public float calculatePrice(@NonNull List<OrderLine> orderLines) {
        float price = 0;

        for (OrderLine orderLine: orderLines) {
            price += orderLine.getCost();
        }

        return price;
    }
}
