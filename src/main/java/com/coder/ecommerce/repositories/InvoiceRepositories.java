package com.coder.ecommerce.repositories;

import com.coder.ecommerce.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepositories extends JpaRepository<Invoice,Long> {
}
