package com.coder.ecommerce.services;

import com.coder.ecommerce.entities.InvoiceDetails;
import com.coder.ecommerce.entities.Products;
import com.coder.ecommerce.repositories.InvoiceDetailsRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class InvoiceDetailsServices {
    @Autowired
    private InvoiceDetailsRepositories invoiceDetailsRepository;
    
    @Autowired
    private ProductServices productServices;


    public InvoiceDetails save(InvoiceDetails invoiceDetails) {
        return this.invoiceDetailsRepository.save(invoiceDetails);
    }

    public List<InvoiceDetails> findAll() {
        return invoiceDetailsRepository.findAll();
    }

    public InvoiceDetails findById(Long id) {
        return invoiceDetailsRepository.findById(id).orElse(null);
    }

    public List<InvoiceDetails> findProductById(Long productId) {
        return invoiceDetailsRepository.findProductById(productId);
    }

    public InvoiceDetails update(Long id, InvoiceDetails invoiceDetails) {
        InvoiceDetails invoiceDetailsFind = this.invoiceDetailsRepository.findById(id).orElse(null);
        if(invoiceDetailsFind== null){
            return null;
        }
        invoiceDetailsFind.setInvoiceIds(invoiceDetails.getInvoiceIds() == null ? invoiceDetailsFind.getInvoiceIds() : invoiceDetails.getInvoiceIds());
        invoiceDetailsFind.setAmount(invoiceDetails.getAmount() == 0 ? invoiceDetailsFind.getAmount() : invoiceDetails.getAmount());
        invoiceDetailsFind.setPrice(invoiceDetails.getPrice() == 0 ? invoiceDetailsFind.getPrice() : invoiceDetails.getPrice());
        this.invoiceDetailsRepository.save(invoiceDetailsFind);
        return invoiceDetailsFind;

    }

    public InvoiceDetails delete(Long id) {
        InvoiceDetails invoiceDetails = invoiceDetailsRepository.findById(id).orElse(null);
        if (invoiceDetails == null) {
            return null;
        }
        invoiceDetailsRepository.delete(invoiceDetails);
        return invoiceDetails;
    }
}
