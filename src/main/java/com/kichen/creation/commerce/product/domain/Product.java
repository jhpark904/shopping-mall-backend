package com.kichen.creation.commerce.product.domain;

import com.kichen.creation.commerce.product.dto.ProductDto;
import com.kichen.creation.commerce.product.dto.ProductResponseDto;
import com.kichen.creation.commerce.product.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
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

    public ProductResponseDto toProductResponseDto() {
        return new ProductResponseDto(
                id,
                name,
                price,
                stock
        );
    }

    public void update(ProductDto productDto) {
        name = productDto.getName();
        price = productDto.getPrice().floatValue();
        stock = productDto.getStock();
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
