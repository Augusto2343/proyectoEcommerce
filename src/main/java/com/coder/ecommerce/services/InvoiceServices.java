package com.coder.ecommerce.services;

import com.coder.ecommerce.entities.Invoice;
import com.coder.ecommerce.repositories.InvoiceRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServices {
    @Autowired
    private InvoiceRepositories invoicesRepository;
    public Invoice save(Invoice invoice){
        return this.invoicesRepository.save(invoice);
    }
    public List<Invoice> findAll(){return this.invoicesRepository.findAll();}
    public Optional<Invoice> update(Long id, Invoice invoice){
        Optional<Invoice> invoiceOptional = this.invoicesRepository.findById(id);
        if(invoiceOptional.isEmpty()){
            return Optional.empty();
        }
        invoiceOptional.get().setCreatedAt(invoice.getCreatedAt());
        invoiceOptional.get().setTotal(invoice.getTotal());

        this.invoicesRepository.save(invoiceOptional.get());
        return invoiceOptional;

    }
    public Optional<Invoice> delete(Long id){
        Optional<Invoice> invoiceOptional = this.invoicesRepository.findById(id);
        if(invoiceOptional.isEmpty()){
            return Optional.empty();
        }
        this.invoicesRepository.delete(invoiceOptional.get());

        return invoiceOptional;
    }
}
