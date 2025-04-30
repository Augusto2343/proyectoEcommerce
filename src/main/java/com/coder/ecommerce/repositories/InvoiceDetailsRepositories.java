package com.coder.ecommerce.repositories;

import com.coder.ecommerce.entities.InvoiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceDetailsRepositories extends JpaRepository<InvoiceDetails,Long> {
}
