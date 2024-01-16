package com.kichen.creation.commerce.product.domain;

import com.kichen.creation.commerce.product.dto.ProductDto;
import com.kichen.creation.commerce.product.dto.ProductResponseDto;
import com.kichen.creation.commerce.product.exception.NotEnoughStockException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductTest {

    String testName = "test";
    Long testId = 0L;
    float testPrice = 1f;
    int testStock = 0;
    Product product = new Product(
            testId,
            testName,
            testPrice,
            testStock
    );

    @Test
    void addStockPositive() {
        int quantity = 3;
        product.addStock(quantity);
        Assertions.assertThat(product.getStock()).isEqualTo(testStock + quantity);
    }

    @Test
    void addStockNegative() {
        int quantity = - 1;
        assertThrows(IllegalArgumentException.class, () -> product.addStock(quantity));
    }

    @Test
    void removeStockSuccess() {
        int quantityAdd = testStock + 3;
        product.addStock(quantityAdd);
        int quantityRemove = testStock + 2;
        product.removeStock(quantityRemove);
        Assertions.assertThat(product.getStock()).isEqualTo(quantityAdd - quantityRemove);
    }

    @Test
    void removeStockFailure() {
        int quantityAdd = testStock + 3;
        product.addStock(quantityAdd);
        int quantityRemove = testStock + 4;
        assertThrows(NotEnoughStockException.class, () -> product.removeStock(quantityRemove));
    }

    @Test
    void removeStockNegative() {
        int quantity = -1;
        assertThrows(IllegalArgumentException.class, () -> product.removeStock(quantity));
    }

    @Test
    void toProductDto() {
        ProductResponseDto dto = product.toProductResponseDto();
        Assertions.assertThat(dto.getId()).isEqualTo(product.getId());
        Assertions.assertThat(dto.getStock()).isEqualTo(product.getStock());
        Assertions.assertThat(dto.getName()).isEqualTo(product.getName());
        Assertions.assertThat(dto.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    void updateFromDto() {

        String newName = "newTest";
        BigDecimal newPrice = BigDecimal.valueOf(2f);
        int newStock = 12;

        ProductDto productDto = mock();
        when(productDto.getName()).thenReturn(newName);
        when(productDto.getPrice()).thenReturn(newPrice);
        when(productDto.getStock()).thenReturn(newStock);
        product.update(productDto);

        Assertions.assertThat(product.getName()).isEqualTo(newName);
        Assertions.assertThat(product.getPrice()).isEqualTo(newPrice.floatValue());
        Assertions.assertThat(product.getStock()).isEqualTo(newStock);
    }
}