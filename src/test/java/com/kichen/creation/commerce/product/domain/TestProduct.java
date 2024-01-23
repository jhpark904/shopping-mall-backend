package com.kichen.creation.commerce.product.domain;

import com.kichen.creation.commerce.product.exception.NotEnoughStockException;

public class TestProduct extends Product {
    private int stock;

    public TestProduct(String name, float price, int stock) {
        super(name, price, stock);
        this.stock = stock;
    }

    @Override
    public int getStock() {
        return stock;
    }

    @Override
    public void removeStock(int quantity) {
        validateQuantityPositive(quantity);
        validateQuantityLessThanStock(quantity);
        System.out.println("adding a delay...");
        stock -= quantity;
    }

    private void validateQuantityPositive(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity argument has to be positive!");
        }
    }

    private void validateQuantityLessThanStock(int quantity) {
        if (quantity > stock) {
            throw new NotEnoughStockException("Quantity argument is greater than the remaining stock!");
        }
    }
}
