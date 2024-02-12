package com.kitchen.creation.commerce.product.service;

import com.kitchen.creation.commerce.global.exception.product.ProductNotFoundException;
import com.kitchen.creation.commerce.product.domain.Product;
import com.kitchen.creation.commerce.product.dto.ProductDto;
import com.kitchen.creation.commerce.product.dto.ProductResponseDto;
import com.kitchen.creation.commerce.product.repository.ProductRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public void createProduct(@NonNull ProductDto productDto) {
        Product product = new Product(
                productDto.getName(),
                productDto.getPrice().floatValue(),
                productDto.getStock()
        );

        productRepository.save(product).toProductResponseDto();
    }

    @Transactional
    public void editProduct(@NotNull Long id, @NonNull ProductDto productDto) {
        Product product = findProductFromRepository(id);
        product.update(productDto);
        productRepository.save(product);
    }

    public ProductResponseDto findProduct(@NonNull Long id) {
        return findProductFromRepository(id).toProductResponseDto();
    }

    public List<ProductResponseDto> findAllProducts() {
        return productRepository.findAll().stream()
                .map(Product::toProductResponseDto).toList();
    }

    private Product findProductFromRepository(@NonNull Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product is not found!")
        );
    }
}
