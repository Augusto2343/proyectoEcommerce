package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.InvoiceDetails;
import com.coder.ecommerce.entities.Products;
import com.coder.ecommerce.services.InvoiceDetailsServices;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/invoice-details")
public class InvoiceDetailsController {

    @Autowired
    private InvoiceDetailsServices invoiceDetailsServices;

    @GetMapping
    public ResponseEntity<List<InvoiceDetails>> findAll(){
        return ResponseEntity.ok(this.invoiceDetailsServices.findAll());
    }
    @GetMapping("/{id}")
    public  ResponseEntity<InvoiceDetails> findById(@PathVariable Long id){
        return ResponseEntity.ok(invoiceDetailsServices.findById(id));
    }
    @GetMapping("/products/{productId}")
    public ResponseEntity<List<InvoiceDetails>> findProductById(@PathVariable Long productId){
        return ResponseEntity.ok(invoiceDetailsServices.findProductById(productId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InvoiceDetails> save(@RequestBody InvoiceDetails invoiceDetails) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Products product = restTemplate.getForObject(
                    "http://localhost:5000/products/{id}",
                    Products.class,
                    invoiceDetails.getIdProducto()
            );
            if(product == null){
                return ResponseEntity.badRequest().build();
            }
            if(invoiceDetails.getAmount()>product.getStock()){
                return ResponseEntity.badRequest().build();
            }
            invoiceDetails.setPrice(product.getPrice()* invoiceDetails.getAmount());
            product.setStock(product.getStock() - invoiceDetails.getAmount());
            return ResponseEntity.ok(invoiceDetailsServices.save(invoiceDetails));

            }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InvoiceDetails> update(@PathVariable Long id,@RequestBody InvoiceDetails invoiceDetails){
        return ResponseEntity.ok(invoiceDetailsServices.update(id, invoiceDetails));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<InvoiceDetails> delete(@PathVariable Long id){
        return ResponseEntity.ok(invoiceDetailsServices.delete(id));

    }
}
