package com.kichen.creation.commerce.product.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import java.util.Objects;

@Getter
@EqualsAndHashCode
public class ProductResponseDto {
    private final Long id;

    private final String name;

    private final float price;

    private final int stock;

    public static final Long MAX_PRICE = 9999999999L;

    public static final Long MAX_STOCK = 9999999999L;

    public ProductResponseDto(Long id, String name, float price, int stock) {
        validateId(id);
        validateName(name);
        validatePrice(price);
        validateStock(stock);

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    private void validateId(Long id) {
        Objects.requireNonNull(id);
    }

    private void validateStock(int stock) {
        if (stock < 0 || stock > MAX_STOCK) {
            throw new IllegalArgumentException("Invalid Stock: Stock is out of range");
        }
    }

    private void validatePrice(float price) {
        if (price < 1 || price > MAX_PRICE) {
            throw new IllegalArgumentException("Invalid Price: Price is out of range");
        }

        String priceString = String.valueOf(price);
        int dotIndex = priceString.indexOf('.');
        if (dotIndex != -1) {
            int fractionalPartLength = priceString.length() - dotIndex - 1;
            if (fractionalPartLength > 2) {
                throw new IllegalArgumentException("Invalid Price: Has more than 2 decimals");
            }
        }
    }

    private void validateName(String name) {
        Objects.requireNonNull(name);
        if (name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Invalid Name: Empty or blank");
        }
    }
}
