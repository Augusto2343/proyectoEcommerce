package com.coder.ecommerce.repositories;

import com.coder.ecommerce.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProductsRepositories extends JpaRepository<Products, Long> {
}
