package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.InvoiceDetails;
import com.coder.ecommerce.services.InvoiceDetailsServices;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

@RestController
@RequestMapping("/invoice-details")
public class InvoiceDetailsController {

    @Autowired
    private InvoiceDetailsServices invoiceDetailsServices;

    @GetMapping
    public ResponseEntity<List<InvoiceDetails>> findAll(){
        return ResponseEntity.ok(this.invoiceDetailsServices.findAll());
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InvoiceDetails> save(@RequestBody InvoiceDetails invoiceDetails){
        try{
            InvoiceDetails newInvoiceDetails = this.invoiceDetailsServices.save(invoiceDetails);
            return ResponseEntity.created(URI.create("/invoice-details/" + newInvoiceDetails.getId())).body(newInvoiceDetails);
        }catch(Exception error){
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<InvoiceDetails>> update(@PathVariable Long id,@RequestBody InvoiceDetails invoiceDetails){
        Optional<InvoiceDetails> invoiceDetailsOptional = this.invoiceDetailsServices.update(id, invoiceDetails);
        if(invoiceDetailsOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invoiceDetailsOptional);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<InvoiceDetails>> delete(@PathVariable Long id){
        Optional<InvoiceDetails> invoiceDetailsOptional= this.invoiceDetailsServices.delete(id);
        if(invoiceDetailsOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invoiceDetailsOptional);
    }
}
