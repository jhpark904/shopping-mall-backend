package com.kichen.creation.commerce.product.repository;

import com.kichen.creation.commerce.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}