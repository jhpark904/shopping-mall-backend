package com.kichen.creation.commerce.repository;

import com.kichen.creation.commerce.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}