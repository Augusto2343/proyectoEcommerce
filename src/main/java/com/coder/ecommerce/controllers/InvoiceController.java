package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.entities.Invoice;
import com.coder.ecommerce.entities.InvoiceDetails;
import com.coder.ecommerce.services.InvoiceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequestMapping("/invoices")
public class InvoiceController {
@Autowired
    private InvoiceServices invoiceServices;
    @GetMapping
    public ResponseEntity<List<Invoice>> findAll(){return ResponseEntity.ok(this.invoiceServices.findAll());}
    @GetMapping("/invoice-details/{invoiceDetailsId}")
    public ResponseEntity<List<Invoice>> getInvoicesByClientId(@PathVariable("id") Long clientId) {
        try {
            List<Invoice> invoices = invoiceServices.findInvoicesByClientId(clientId);
            if (invoices.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 No Content
            }
            return ResponseEntity.ok(invoices); // 200 OK con la lista de facturas
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request si algo sale mal
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Invoice> findById(@PathVariable Long id){return ResponseEntity.ok(invoiceServices.findById(id));}
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Invoice> save(@RequestBody Invoice invoice){
        try{
            RestTemplate restTemplate = new RestTemplate();
            Client existingClient = restTemplate.getForObject(
                    "http://localhost:5000/clients/{id}",
                    Client.class,
                    invoice.getIdClient()

            );

            InvoiceDetails invoiceDetails =restTemplate.getForObject(
                    "http://localhost:5000/invoice-details/{id}",
                    InvoiceDetails.class,
                    invoice.getIdInvoiceDetails()
            );

            if( invoiceDetails == null || existingClient == null){
                System.out.println("what happened");
                return ResponseEntity.badRequest().build();
            }
            existingClient.setInvoiceIds(existingClient.getInvoiceIds());
            return ResponseEntity.ok(invoiceServices.save(invoice));
        }catch(Exception error){
            System.out.println("aiaiaiaiai");
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Invoice>  update(@PathVariable Long id, @RequestBody Invoice invoice){
        try{
            RestTemplate restTemplate = new RestTemplate();
            InvoiceDetails invoiceDetails =restTemplate.getForObject(
                    "http://localhost:5000/invoice-details/{id}",
                    InvoiceDetails.class,
                    invoice.getIdInvoiceDetails()
                    );
            Client newClient = restTemplate.getForObject(
                    "http://localhost:5000/clients/{id}",
                    Client.class,
                    invoice.getIdClient()
                    );
            if( invoiceDetails == null || newClient == null){
                System.out.println("what happened");
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(invoiceServices.save(invoice));
        }catch(Exception error){
            System.out.println("wa happen"+error);
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Invoice> delete(@PathVariable Long id){
        Invoice invoiceToDelete= invoiceServices.delete(id);
        if(invoiceToDelete == null){
            return ResponseEntity.notFound().build();
            }
        return ResponseEntity.ok(invoiceToDelete);

    }


}
