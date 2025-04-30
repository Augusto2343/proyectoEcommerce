package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.Invoice;
import com.coder.ecommerce.services.InvoiceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
@Autowired
    private InvoiceServices invoiceServices;
    @GetMapping
    public ResponseEntity<List<Invoice>> findAll(){
        return ResponseEntity.ok(this.invoiceServices.findAll());

    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Invoice> save(@RequestBody Invoice invoice){
        try{
            Invoice newInvoice = this.invoiceServices.save(invoice);
            return ResponseEntity.created(URI.create("/invoices/" + newInvoice.getId())).body(newInvoice);

        }catch(Exception error){
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Invoice>>  update(@PathVariable Long id,@RequestBody Invoice invoice){
        Optional<Invoice> invoiceOptional= this.invoiceServices.update(id, invoice);
        if(invoiceOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invoiceOptional);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Invoice>> delete(@PathVariable Long id){
        Optional<Invoice> invoiceOptional= this.invoiceServices.delete(id);
        if(invoiceOptional.isEmpty()){
            return ResponseEntity.notFound().build();
            }
        return ResponseEntity.ok(invoiceOptional);

    }


}
