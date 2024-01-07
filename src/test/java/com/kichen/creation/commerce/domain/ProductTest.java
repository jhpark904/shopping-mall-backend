package com.kichen.creation.commerce.domain;

import com.kichen.creation.commerce.dto.ProductDto;
import com.kichen.creation.commerce.exception.NotEnoughStockException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
        ProductDto dto = product.toProductDto();
        Assertions.assertThat(dto.id()).isEqualTo(product.getId());
        Assertions.assertThat(dto.stock()).isEqualTo(product.getStock());
        Assertions.assertThat(dto.name()).isEqualTo(product.getName());
        Assertions.assertThat(dto.price()).isEqualTo(product.getPrice());
    }

    @Test
    void updateFromDto() {

        String newName = "newTest";
        float newPrice = 2f;
        int newStock = 12;

        ProductDto productDto = mock();
        when(productDto.name()).thenReturn(newName);
        when(productDto.price()).thenReturn(newPrice);
        when(productDto.stock()).thenReturn(newStock);
        product.updateFromDto(productDto);

        Assertions.assertThat(product.getName()).isEqualTo(newName);
        Assertions.assertThat(product.getPrice()).isEqualTo(newPrice);
        Assertions.assertThat(product.getStock()).isEqualTo(newStock);
    }
}