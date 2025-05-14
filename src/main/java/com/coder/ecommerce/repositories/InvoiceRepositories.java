package com.coder.ecommerce.repositories;

import com.coder.ecommerce.entities.Invoice;
import com.coder.ecommerce.entities.InvoiceDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepositories extends JpaRepository<Invoice, Long> {
        List<Invoice> findByClientId(Long clientId);
    }

