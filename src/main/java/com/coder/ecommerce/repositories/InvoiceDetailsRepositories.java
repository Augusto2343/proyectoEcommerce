package com.coder.ecommerce.repositories;

import com.coder.ecommerce.entities.InvoiceDetails;
import com.coder.ecommerce.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDetailsRepositories extends JpaRepository<InvoiceDetails, Long> {
    public List<InvoiceDetails> findProductById (Long productId);
}
