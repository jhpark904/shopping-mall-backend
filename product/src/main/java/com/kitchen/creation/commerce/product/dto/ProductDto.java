package com.kitchen.creation.commerce.product.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class ProductDto {
    @NotBlank(message = "Invalid Product Name: Name is Blank")
    @NotEmpty(message = "Invalid Product Name: Name is Empty")
    @NotNull(message = "Invalid Product Name: Name is Null")
    private final String name;

    @Min(value = 1, message = "Invalid Price: Equals to zero or Less than zero")
    @Digits(integer = 10, fraction = 2, message = "Invalid price: digit out of range")
    private final BigDecimal price;

    @Min(value = 0, message = "Invalid Stock: Less than 0")
    @Digits(integer = 10, fraction = 0, message = "Invalid price: digit out of range")
    private final int stock;
}
