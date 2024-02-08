package com.kitchen.creation.commerce.product.service;

import com.kitchen.creation.commerce.product.domain.Product;
import com.kitchen.creation.commerce.product.dto.ProductDto;
import com.kitchen.creation.commerce.product.dto.ProductResponseDto;
import com.kitchen.creation.commerce.product.exception.ProductNotFoundException;
import com.kitchen.creation.commerce.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    ProductRepository productRepository = mock();
    ProductService productService = new ProductService(productRepository);
    Long testId = 1L;
    ProductDto testProductDto = new ProductDto(
            "test",
            BigDecimal.valueOf(1f),
            0
    );

    ProductResponseDto testProductResponseDto = new ProductResponseDto(
            testId,
            "test",
            1f,
            0
    );

    Product testProduct = mock();

    @Test
    void createProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);
        productService.createProduct(testProductDto);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void editProduct() {
        when(productRepository.findById(testId)).thenReturn(Optional.of(testProduct));
        productService.editProduct(testId, testProductDto);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void editProductDoesNotExist() {
        when(productRepository.findById(testId)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.editProduct(testId, testProductDto));
    }

    @Test
    void findProduct() {
        Long testId = 1L;
        when(productRepository.findById(testId)).thenReturn(Optional.of(testProduct));
        when(testProduct.toProductResponseDto()).thenReturn(testProductResponseDto);
        productService.findProduct(testId);
        verify(productRepository).findById(testId);
    }

    @Test
    void findProductDoesNotExist() {
        when(productRepository.findById(testId)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.findProduct(testId));
    }

    @Test
    void findAllProducts() {
        productService.findAllProducts();
        verify(productRepository).findAll();
    }
}