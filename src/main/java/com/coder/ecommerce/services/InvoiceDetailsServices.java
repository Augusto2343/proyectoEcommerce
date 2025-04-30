package com.coder.ecommerce.services;

import com.coder.ecommerce.entities.InvoiceDetails;
import com.coder.ecommerce.entities.InvoiceDetails;
import com.coder.ecommerce.repositories.InvoiceDetailsRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceDetailsServices {
    @Autowired
    private InvoiceDetailsRepositories invoiceDetailsRepository;
    public InvoiceDetails save(InvoiceDetails invoiceDetails){
        return this.invoiceDetailsRepository.save(invoiceDetails);
    }
    public List<InvoiceDetails> findAll(){return this.invoiceDetailsRepository.findAll();}
    public Optional<InvoiceDetails> update(Long id, InvoiceDetails invoiceDetails){
        Optional<InvoiceDetails> invoiceDetailsOptional = this.invoiceDetailsRepository.findById(id);
        if(invoiceDetailsOptional.isEmpty()){
            return Optional.empty();
        }
        invoiceDetailsOptional.get().setAmount(invoiceDetails.getAmount());
        invoiceDetailsOptional.get().setPrice(invoiceDetails.getPrice());
        this.invoiceDetailsRepository.save(invoiceDetailsOptional.get());
        return invoiceDetailsOptional;

    }
    public Optional<InvoiceDetails> delete(Long id){
        Optional<InvoiceDetails> invoiceDetailsOptional = this.invoiceDetailsRepository.findById(id);
        if(invoiceDetailsOptional.isEmpty()){
            return Optional.empty();
        }
        this.invoiceDetailsRepository.delete(invoiceDetailsOptional.get());

        return invoiceDetailsOptional;
    }
}
