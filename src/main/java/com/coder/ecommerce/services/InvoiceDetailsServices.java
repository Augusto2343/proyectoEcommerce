package com.coder.ecommerce.services;


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
    public InvoiceDetails save(InvoiceDetails invoiceDetails){return this.invoiceDetailsRepository.save(invoiceDetails);}
    public List<InvoiceDetails> findAll(){return invoiceDetailsRepository.findAll();}

    public InvoiceDetails findById(Long id){return invoiceDetailsRepository.findById(id).orElse(null);}
    public List<InvoiceDetails> findProductById(Long productId){return invoiceDetailsRepository.findProductById(productId);}
    public InvoiceDetails update(Long id, InvoiceDetails invoiceDetails){
        InvoiceDetails invoiceDetailsFind = this.invoiceDetailsRepository.findById(id).orElse(null);
        if(invoiceDetailsFind== null){
            return null;
        }
        invoiceDetailsFind.setAmount(invoiceDetailsFind.getAmount());
        invoiceDetailsFind.setPrice(invoiceDetailsFind.getPrice());
        this.invoiceDetailsRepository.save(invoiceDetailsFind);
        return invoiceDetailsFind;

    }
    public InvoiceDetails delete(Long id){
        InvoiceDetails invoiceDetails = invoiceDetailsRepository.findById(id).orElse(null);
        if(invoiceDetails==null){
            return null;
        }
        invoiceDetailsRepository.delete(invoiceDetails);

        return invoiceDetails;
    }
}
