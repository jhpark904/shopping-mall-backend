package com.kichen.creation.commerce.product.service;

import com.kichen.creation.commerce.product.domain.Product;
import com.kichen.creation.commerce.product.dto.ProductDto;
import com.kichen.creation.commerce.product.dto.ProductResponseDto;
import com.kichen.creation.commerce.product.exception.ProductNotFoundException;
import com.kichen.creation.commerce.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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
        when(productRepository.getReferenceById(testId)).thenReturn(testProduct);
        productService.editProduct(testId, testProductDto);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void editProductDoesNotExist() {
        when(productRepository.getReferenceById(testId)).thenThrow(EntityNotFoundException.class);
        assertThrows(ProductNotFoundException.class, () -> productService.editProduct(testId, testProductDto));
    }

    @Test
    void findProduct() {
        Long testId = 1L;
        when(productRepository.getReferenceById(testId)).thenReturn(testProduct);
        when(testProduct.toProductResponseDto()).thenReturn(testProductResponseDto);
        productService.findProduct(testId);
        verify(productRepository).getReferenceById(testId);
    }

    @Test
    void findProductDoesNotExist() {
        when(productRepository.getReferenceById(testId)).thenThrow(EntityNotFoundException.class);
        assertThrows(ProductNotFoundException.class, () -> productService.findProduct(testId));
    }

    @Test
    void findAllProducts() {
        productService.findAllProducts();
        verify(productRepository).findAll();
    }
}