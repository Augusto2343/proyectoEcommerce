package com.coder.ecommerce.repositories;

import com.coder.ecommerce.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepositories extends JpaRepository<Products, Long> {
}
