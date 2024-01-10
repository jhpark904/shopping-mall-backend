package com.kichen.creation.commerce.product.controller;

import com.kichen.creation.commerce.product.dto.ProductDto;
import com.kichen.creation.commerce.product.dto.ProductResponseDto;
import com.kichen.creation.commerce.global.response.SuccessResponse;
import com.kichen.creation.commerce.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductV1Controller {

    private final ProductService productService;

    @GetMapping("/products")
    public SuccessResponse<List<ProductResponseDto>> getAllProducts() {
        return new SuccessResponse<>(HttpStatus.OK.value(), "Success", productService.findAllProducts());
    }

    @GetMapping("/products/{id}")
    public SuccessResponse<ProductResponseDto> getProduct(@PathVariable Long id) {
        return new SuccessResponse<>(HttpStatus.OK.value(), "Success", productService.findProduct(id));
    }

    @PostMapping("/products")
    public SuccessResponse<?> createNewProduct(@RequestBody @Valid ProductDto productDto) {
       productService.createProduct(productDto);
       return new SuccessResponse<>(HttpStatus.OK.value(), "Success");
    }

    @PutMapping("/products/{id}")
    public SuccessResponse<?> editProduct(@PathVariable Long id, @RequestBody @Valid ProductDto productDto) {
        productService.editProduct(id, productDto);
        return new SuccessResponse<>(HttpStatus.OK.value(), "Success");
    }
}
