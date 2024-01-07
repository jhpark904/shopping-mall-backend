package com.kichen.creation.commerce.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductDtoTest {

    @Test
    void createDtoSuccess() {
        Long id = 0L;
        String name = "test";
        float price = 1f;
        int stock = 0;

        ProductDto productDto = new ProductDto(
                id,
                name,
                price,
                stock
        );

        Assertions.assertThat(productDto.id()).isEqualTo(id);
        Assertions.assertThat(productDto.name()).isEqualTo(name);
        Assertions.assertThat(productDto.price()).isEqualTo(price);
        Assertions.assertThat(productDto.stock()).isEqualTo(stock);
    }

    @Test
    void createDtoNullId() {
        Long id = null;
        String name = "test";
        float price = 1f;
        int stock = 0;

        assertThrows(
                IllegalArgumentException.class,
                () -> new ProductDto(id, name, price, stock)
        );
    }

    @Test
    void createDtoNullName() {
        Long id = 0L;
        String name = null;
        float price = 1f;
        int stock = 0;

        assertThrows(
                IllegalArgumentException.class,
                () -> new ProductDto(id, name, price, stock)
        );
    }

    @Test
    void createDtoEmptyName() {
        Long id = 0L;
        String name = "";
        float price = 1f;
        int stock = 0;

        assertThrows(
                IllegalArgumentException.class,
                () -> new ProductDto(id, name, price, stock)
        );
    }

    @Test
    void createDtoNegativePrice() {
        Long id = 0L;
        String name = "test";
        float price = -1f;
        int stock = 0;

        assertThrows(
                IllegalArgumentException.class,
                () -> new ProductDto(id, name, price, stock)
        );
    }

    @Test
    void createDtoNegativeStock() {
        Long id = 0L;
        String name = "test";
        float price = 1f;
        int stock = -1;

        assertThrows(
                IllegalArgumentException.class,
                () -> new ProductDto(id, name, price, stock)
        );
    }

}