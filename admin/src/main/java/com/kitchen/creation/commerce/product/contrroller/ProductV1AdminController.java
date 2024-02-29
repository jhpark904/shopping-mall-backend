package com.kitchen.creation.commerce.product.contrroller;

import com.kitchen.creation.commerce.product.dto.ProductDto;
import com.kitchen.creation.commerce.product.service.ProductService;
import com.kitchen.creation.commerce.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class ProductV1AdminController {

    private final ProductService productService;

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
