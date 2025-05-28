package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.entities.Invoice;
import com.coder.ecommerce.entities.InvoiceDetails;
import com.coder.ecommerce.entities.Products;
import com.coder.ecommerce.services.InvoiceServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Tag(name="Endpoint invoices", description = "CRUD de invoices de la tienda")

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceServices invoiceServices;

    //Documentamos con Swagger el findAll
    @Operation(
            summary = "Listamos los invoices que existen",
            description = "Devuelve una lista de los invoices que existen"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Devuelve una lista de los invoices creados",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Invoice.class)
                    )}
    )

    @GetMapping
    public ResponseEntity<List<Invoice>> findAll() {
        return ResponseEntity.ok(this.invoiceServices.findAll());
    }

//Documentamos con Swagger el findById
    @Operation(
            summary = "Devuelve el invoice solicitado por el usuario",
            description = "Devuelve el invoice solicitado por el usuario"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Devuelve una lista de los invoice details creados",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Invoice.class)
                    )}
    )
    @GetMapping("/{id}")
        public ResponseEntity<Invoice> findById(@PathVariable Long id){return ResponseEntity.ok(invoiceServices.findById(id));
    }
    //Documentamos con Swagger el save
    @Operation(
            summary = "Crea un nuevo invoice",
            description = "Crea un nuevo invoice con los datos enviados"
    )
    @ApiResponses(
            value={
                    @ApiResponse(
                            responseCode = "200",
                            description = "✅ Invoice creado exitosamente",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class))}
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "❌ El Invoice no pudo ser creado",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class))}
                    )
            }
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Invoice> save(@RequestBody Invoice invoice){
            try{
                int total = 0;
                RestTemplate restTemplate = new RestTemplate();
                Client existingClient = restTemplate.getForObject(
                        "http://localhost:5000/clients/{id}",
                        Client.class,
                        invoice.getIdDelCliente()

                );
                if( existingClient == null){
                    return ResponseEntity.badRequest().build();
                }
                for (Long invoiceDetailsId : invoice.getIdInvoiceDetails()) {
                    InvoiceDetails invoiceDetails =restTemplate.getForObject(
                            "http://localhost:5000/invoice-details/{id}",
                            InvoiceDetails.class,
                            invoiceDetailsId
                    );
                    if( invoiceDetails == null ){
                        return ResponseEntity.notFound().build();
                    }
                    if(invoiceDetails.getPrice() == null){
                        return ResponseEntity.badRequest().build();
                    }
                    total += invoiceDetails.getPrice();

                    invoice.setTotal(total);
                }


                invoice.setCreatedAt(new Date());

                Invoice savedInvoice = invoiceServices.save(invoice);
                for (Long invoiceDetailsId : invoice.getIdInvoiceDetails()) {
                    InvoiceDetails invoiceDetails = restTemplate.getForObject(
                            "http://localhost:5000/invoice-details/{id}",
                            InvoiceDetails.class,
                            invoiceDetailsId
                    );
                    invoiceDetails.getInvoiceIds().add(invoice.getId());
                    invoiceDetails.setPrice(invoiceDetails.getPrice());
                    invoiceDetails.setAmount(invoiceDetails.getAmount());
                    restTemplate.put(
                            "http://localhost:5000/invoice-details/{id}",
                            invoiceDetails,
                            invoiceDetailsId
                    );
                }
                existingClient.getInvoiceIds().add(savedInvoice.getId());
                restTemplate.put(
                        "http://localhost:5000/clients/{id}",
                        existingClient,
                        invoice.getIdDelCliente()
                );

                existingClient.setInvoiceIds(existingClient.getInvoiceIds());


                return ResponseEntity.ok(invoice);
            }catch(Exception error){
                error.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
    }
    //Documentamos con Swagger el update
    @Operation(
            summary = "Modifica el invoice solicitado por el usuario",
            description = "Modifica el invoice solicitado por el usuario con lo que ingresas"
    )
    @ApiResponses(
            value={
                    @ApiResponse(
                            responseCode = "200",
                            description = "✅ Invoice actualizado exitosamente",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class))}
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "❌ El Invoice no pudo ser actualizado",content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class))}
                    )
            }
    )
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Invoice>  update(@PathVariable Long id, @RequestBody Invoice invoice, @RequestParam(defaultValue = "false") String deleting) {
        Invoice oldI = invoiceServices.findById(id);
        if(deleting =="false") {
            try {
                Double total = 0.0;
                int stockSolicitado = 0;
                Invoice oldInvoice = invoiceServices.findById(id);
                RestTemplate restTemplate = new RestTemplate();
                if ((invoice.getIdDelCliente() == null && invoice.getIdInvoiceDetails().size() == 0) || (invoice.getIdDelCliente().equals(oldInvoice.getIdDelCliente()) && invoice.getIdInvoiceDetails().equals(oldInvoice.getIdInvoiceDetails()))) {
                    return ResponseEntity.badRequest().build();
                }
                if (oldInvoice.getIdDelCliente() != invoice.getIdDelCliente() && invoice.getIdDelCliente() != null) {

                    //Obtener el cliente antiguo
                    Client oldClient = restTemplate.getForObject(
                            "http://localhost:5000/clients/{id}",
                            Client.class,
                            oldInvoice.getIdDelCliente()
                    );

                    //Obtener el cliente nuevo
                    Client newClient = restTemplate.getForObject(
                            "http://localhost:5000/clients/{id}",
                            Client.class,
                            invoice.getIdDelCliente()
                    );
                    //Confirmar si el cliente nuevo existe
                    if (newClient == null) {
                        return ResponseEntity.badRequest().build();
                    }

                    //Actualizar el cliente antiguo
                    oldClient.getInvoiceIds().remove(oldInvoice.getId());

                    restTemplate.put(
                            "http://localhost:5000/clients/{id}",
                            oldClient,
                            oldClient.getId()
                    );


                    //Actualizar el cliente nuevo
                    newClient.getInvoiceIds().add(invoice.getId());
                    restTemplate.put(
                            "http://localhost:5000/clients/{id}",
                            newClient,
                            newClient.getId()
                    );
                }

                //Confirmar si se cambió el invoiceDetailsId
                if (invoice.getIdInvoiceDetails() != null &&
                        !invoice.getIdInvoiceDetails().equals(oldInvoice.getIdInvoiceDetails())) {
                    if (oldInvoice.getIdInvoiceDetails().size() > 0) {
                        for (Long invoiceDetailsId : oldInvoice.getIdInvoiceDetails()) {
                            try {
                                InvoiceDetails oldDetail = restTemplate.getForObject(
                                        "http://localhost:5000/invoice-details/{id}",
                                        InvoiceDetails.class,
                                        invoiceDetailsId
                                );
                                if (oldDetail == null) {
                                    oldInvoice.getIdInvoiceDetails().remove(invoiceDetailsId);
                                } else {
                                    oldDetail.getInvoiceIds().remove(oldInvoice.getId());
                                    oldDetail.setPrice(oldDetail.getPrice());

                                    restTemplate.put(
                                            "http://localhost:5000/invoice-details/{id}",
                                            oldDetail,
                                            oldDetail.getId()
                                    );
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    }
                }
                for (Long invoiceDetailsId : invoice.getIdInvoiceDetails()) {
                    InvoiceDetails newDetail = restTemplate.getForObject(
                            "http://localhost:5000/invoice-details/{id}",
                            InvoiceDetails.class,
                            invoiceDetailsId
                    );

                    if (newDetail == null) return ResponseEntity.badRequest().build();

                    if (!newDetail.getInvoiceIds().contains(oldInvoice.getId())) {
                        newDetail.getInvoiceIds().add(oldInvoice.getId());
                    }
                    System.out.println(newDetail.getInvoiceIds());
                    Long idProducto = newDetail.getIdProducto();
                    Products product = restTemplate.getForObject(
                            "http://localhost:5000/products/{id}",
                            Products.class,
                            idProducto
                    );

                    System.out.println(product);
                    if (product == null) {
                        return ResponseEntity.badRequest().build();
                    }
                    System.out.println(newDetail);
                    newDetail.setPrice(product.getPrice() * newDetail.getAmount());
                    total += newDetail.getPrice();
                    oldI.setTotal(total);

                    restTemplate.put(
                            "http://localhost:5000/invoice-details/{id}",
                            newDetail,
                            invoiceDetailsId
                    );

                }
                oldI.setIdInvoiceDetails(invoice.getIdInvoiceDetails().size() == 0 ? oldI.getIdInvoiceDetails() : invoice.getIdInvoiceDetails());
                oldI.setIdDelCliente(invoice.getIdDelCliente() == null ? oldI.getIdDelCliente() : invoice.getIdDelCliente());
                return ResponseEntity.ok(invoiceServices.update(id, oldI));

            } catch (Exception error) {
                System.out.println("wa happen" + error);
                return ResponseEntity.badRequest().build();
            }
        }
        else{
            try{

                oldI.setIdInvoiceDetails(invoice.getIdInvoiceDetails());
                if(oldI.getIdInvoiceDetails()==null||oldI.getIdInvoiceDetails().size()==0){
                    System.out.println(invoice);
                }
                return ResponseEntity.ok(invoiceServices.update(id, oldI));
            }catch(Exception error){
                error.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        }
    }
    //Documentamos con Swagger el delete
    @Operation(
            summary = "Elimina el invoice solicitado por el usuario",
            description = "Elimina el invoice solicitado por el usuario"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Invoice eliminado exitosamente.",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Invoice.class)
                    )}
    )


    @DeleteMapping("/{id}")
        public ResponseEntity<Invoice> delete(@PathVariable Long id){
            try{
                Invoice invoiceToDelete= invoiceServices.findById(id);
                if(invoiceToDelete == null){
                    return ResponseEntity.notFound().build();
                    }


                Long clientId = invoiceToDelete.getIdDelCliente();
                RestTemplate restTemplate = new RestTemplate();
                Client client = restTemplate.getForObject(
                        "http://localhost:5000/clients/{id}",
                        Client.class,
                        clientId
                );
                if(client !=null){
                    client.getInvoiceIds().remove(invoiceToDelete.getId());
                    restTemplate.put(
                            "http://localhost:5000/clients/{id}",
                            client,
                            clientId
                    );
                }
                for (Long invoiceDetailsId : invoiceToDelete.getIdInvoiceDetails()) {
                    InvoiceDetails invoiceDetails = restTemplate.getForObject(
                            "http://localhost:5000/invoice-details/{id}",
                            InvoiceDetails.class,
                            invoiceDetailsId
                    );
                    invoiceDetails.getInvoiceIds().remove(invoiceToDelete.getId());
                    invoiceDetails.setPrice(invoiceDetails.getPrice());
                    invoiceDetails.setAmount(invoiceDetails.getAmount());
                    invoiceDetails.setIdProducto(invoiceDetails.getIdProducto());
                    try{restTemplate.put(
                            "http://localhost:5000/invoice-details/{id}",
                            invoiceDetails,
                            invoiceDetailsId
                    );}
                    catch (Exception e){
                        System.out.println(invoiceDetails);
                    }
                }
                return ResponseEntity.ok(invoiceServices.delete(id));
            }catch(Exception error){
                error.printStackTrace();
                return ResponseEntity.badRequest().build();
            }

        }


}
