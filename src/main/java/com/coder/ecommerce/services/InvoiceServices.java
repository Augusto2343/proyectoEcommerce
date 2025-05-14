package com.coder.ecommerce.services;

import com.coder.ecommerce.entities.Invoice;
import com.coder.ecommerce.entities.InvoiceDetails;
import com.coder.ecommerce.repositories.InvoiceRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class InvoiceServices {
    @Autowired
    private InvoiceRepositories invoicesRepository;
    public Invoice save(Invoice invoice){return invoicesRepository.save(invoice);}
    public List<Invoice> findAll(){return invoicesRepository.findAll();}
    public Invoice findById(Long id){return invoicesRepository.findById(id).orElse(null);}
    public List<Invoice> findInvoicesByClientId(Long clientId) {
        return invoicesRepository.findByClientId(clientId);
    }
    public Invoice update(Long id, Invoice invoice){
        Invoice invoiceToUpdate = invoicesRepository.findById(id).orElse(null);
        if(invoiceToUpdate==null){
            return null;
        }
        invoiceToUpdate.setCreatedAt(invoiceToUpdate.getCreatedAt());
        invoiceToUpdate.setTotal(invoiceToUpdate.getTotal());

        invoicesRepository.save(invoiceToUpdate);
        return invoiceToUpdate;

    }
    public Invoice delete(Long id){
        Invoice invoiceToDelete = invoicesRepository.findById(id).orElse(null);
        if(invoiceToDelete == null){
            return null;
        }
        invoicesRepository.delete(invoiceToDelete);

        return invoiceToDelete;
    }
}
