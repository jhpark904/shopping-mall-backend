package com.kichen.creation.commerce.dto;

public record ProductDto(Long id, String name, float price, int stock) {
    public ProductDto {
        validateId(id);
        validateName(name);
        validatePrice(price);
        validateStock(stock);
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null!");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty!");
        }
    }

    private void validatePrice(float price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price has to be more than 0!");
        }
    }

    private void validateStock(float stock) {
        if (stock <= -1) {
            throw new IllegalArgumentException("Stock has to be at least 0!");
        }
    }

}
