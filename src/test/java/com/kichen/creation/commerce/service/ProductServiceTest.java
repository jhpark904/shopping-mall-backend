package com.kichen.creation.commerce.service;

import com.kichen.creation.commerce.domain.Product;
import com.kichen.creation.commerce.dto.ProductDto;
import com.kichen.creation.commerce.exception.ProductNotFoundException;
import com.kichen.creation.commerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    ProductRepository productRepository = mock();
    ProductService productService = new ProductService(productRepository);
    Long testId = 1L;
    ProductDto testProductDto = new ProductDto(
            testId,
            "test",
            1f,
            0
    );
    Product testProduct = mock();

    @Test
    void createProduct() {
        productService.createProduct(testProductDto);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void editProduct() {
        when(productRepository.getReferenceById(testId)).thenReturn(testProduct);
        when(testProduct.updateFromDto(testProductDto)).thenReturn(testProduct);
        productService.editProduct(testProductDto);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void editProductDoesNotExist() {
        when(productRepository.getReferenceById(testId)).thenThrow(EntityNotFoundException.class);
        assertThrows(ProductNotFoundException.class, () -> productService.editProduct(testProductDto));
    }

    @Test
    void findProduct() {
        Long testId = 1L;
        when(productRepository.getReferenceById(testId)).thenReturn(testProduct);
        when(testProduct.toProductDto()).thenReturn(testProductDto);
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