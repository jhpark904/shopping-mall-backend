package com.kichen.creation.commerce.domain;

import com.kichen.creation.commerce.dto.ProductDto;
import com.kichen.creation.commerce.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;

    private float price;

    private int stock;

    public Product(String name, Float price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Product(Long id, String name, Float price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public void addStock(int quantity) {
        validateQuantityPositive(quantity);
        stock += quantity;
    }

    public void removeStock(int quantity) {
        validateQuantityPositive(quantity);
        validateQuantityLessThanStock(quantity);
        stock -= quantity;
    }

    public ProductDto toProductDto() {
        return new ProductDto(
                id,
                name,
                price,
                stock
        );
    }

    public Product updateFromDto(ProductDto productDto) {
        name = productDto.name();
        price = productDto.price();
        stock = productDto.stock();
        return this;
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
