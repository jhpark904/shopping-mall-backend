package com.kichen.creation.commerce.product.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


class ProductResponseDtoTest {

    @Test
    void createProductResponseDtoSuccess() {
        Long id = 1L;
        String name = "test";
        float price = 10f;
        int stock = 1;

        ProductResponseDto productResponseDto = new ProductResponseDto(id, name, price, stock);
        Assertions.assertThat(productResponseDto).isEqualTo(new ProductResponseDto(id, name, price, stock));
    }

    @Test
    void createProductResponseDtoNullId() {
        String name = "test";
        float price = 10f;
        int stock = 1;

        assertThrows(NullPointerException.class, () -> new ProductResponseDto(null, name, price, stock));
    }

    @Test
    void createProductResponseDtoNullName() {
        Long id = 1L;
        float price = 10f;
        int stock = 1;

        assertThrows(NullPointerException.class, () -> new ProductResponseDto(id, null, price, stock));
    }

    @Test
    void createProductResponseDtoEmptyName() {
        String name = "";
        Long id = 1L;
        float price = 10f;
        int stock = 1;

        assertThrows(IllegalArgumentException.class, () -> new ProductResponseDto(id, name, price, stock));
    }

    @Test
    void createProductResponseDtoBlankName() {
        String name = "  ";
        Long id = 1L;
        float price = 10f;
        int stock = 1;

        assertThrows(IllegalArgumentException.class, () -> new ProductResponseDto(id, name, price, stock));
    }

    @Test
    void createProductResponseDtoPriceOutOfRange() {
        String name = "  ";
        Long id = 1L;
        float price = -1;
        int stock = 1;

        assertThrows(IllegalArgumentException.class, () -> new ProductResponseDto(id, name, price, stock));
    }

    @Test
    void createProductResponseDtoPriceDigitOutOfRange() {
        String name = "  ";
        Long id = 1L;
        float price = 1.01302f;
        int stock = 1;

        assertThrows(IllegalArgumentException.class, () -> new ProductResponseDto(id, name, price, stock));
    }

    @Test
    void createProductResponseDtoStockOutOfRange() {
        String name = "  ";
        Long id = 1L;
        float price = 10;
        int stock = -1;

        assertThrows(IllegalArgumentException.class, () -> new ProductResponseDto(id, name, price, stock));
    }

}