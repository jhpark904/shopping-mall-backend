package com.kitchen.creation.commerce.product.controller;

import com.kitchen.creation.commerce.product.dto.ProductDto;
import com.kitchen.creation.commerce.product.dto.ProductResponseDto;
import com.kitchen.creation.commerce.global.response.SuccessResponse;
import com.kitchen.creation.commerce.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductV1Controller {

    private final ProductService productService;

    @GetMapping("/v1/products")
    public SuccessResponse<List<ProductResponseDto>> getAllProducts() {
        return new SuccessResponse<>(HttpStatus.OK.value(), "Success", productService.findAllProducts());
    }

    @GetMapping("/v1/products/{id}")
    public SuccessResponse<ProductResponseDto> getProduct(@PathVariable Long id) {
        return new SuccessResponse<>(HttpStatus.OK.value(), "Success", productService.findProduct(id));
    }

    @PostMapping("/v1/products")
    public SuccessResponse<?> createNewProduct(@RequestBody @Valid ProductDto productDto) {
       productService.createProduct(productDto);
       return new SuccessResponse<>(HttpStatus.OK.value(), "Success");
    }

    @PutMapping("/v1/products/{id}")
    public SuccessResponse<?> editProduct(@PathVariable Long id, @RequestBody @Valid ProductDto productDto) {
        productService.editProduct(id, productDto);
        return new SuccessResponse<>(HttpStatus.OK.value(), "Success");
    }
}
