package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.Invoice;
import com.coder.ecommerce.entities.InvoiceDetails;
import com.coder.ecommerce.entities.Products;
import com.coder.ecommerce.services.InvoiceDetailsServices;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name="Endpoint invoice details", description = "CRUD de invoice details de la tienda")

@RestController
@RequestMapping("/invoice-details")
public class InvoiceDetailsController {

    @Autowired
    private InvoiceDetailsServices invoiceDetailsServices;

    //Documentamos con Swagger el findAll
    @Operation(
            summary = "Listamos los invoice details que existen",
            description = "Devuelve una lista de los invoice details que existen"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Devuelve una lista de los invoice details creados",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = InvoiceDetails.class)
                    )}
    )
    @GetMapping
    public ResponseEntity<List<InvoiceDetails>> findAll() {
        return ResponseEntity.ok(this.invoiceDetailsServices.findAll());
    }

    //Documentamos con Swagger el findById
    @Operation(
            summary = "Se muestra el invoice details solicitado por el usuario",
            description = "Devuelve el invoice details solicitado por el usuario"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Devuelve el invoice details solicitado por el usuario",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = InvoiceDetails.class)
                    )}
    )
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDetails> findById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceDetailsServices.findById(id));
    }

    //Documentamos con Swagger el save
    @Operation(
            summary = "Se crea un nuevo invoice details",
            description = "Crea un invoice details con los datos enviados"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "✅ Invoice details creado exitosamente",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetails.class))}
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "❌ El invoice details no pudo ser creado",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetails.class))}
                    )
            }
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InvoiceDetails> save(@RequestBody InvoiceDetails invoiceDetails) {

        try{

            // Obtenemos el producto
            System.out.println(invoiceDetails);
            RestTemplate restTemplate = new RestTemplate();
            Products product = restTemplate.getForObject(
                    "http://localhost:5000/products/{id}",
                    Products.class,
                    invoiceDetails.getIdProducto()
            );

            if (product == null) {
                return ResponseEntity.badRequest().build();
            }
            if(invoiceDetails.getAmount() > product.getStock()) {
                return ResponseEntity.badRequest().build();
            }
            product.setStock(product.getStock() - invoiceDetails.getAmount());
            product.setPrice(product.getPrice());
            product.setCode(product.getCode());
            product.setDescription(product.getDescription());
            product.getInvoiceDetailsIds().add(invoiceDetails.getId());
            restTemplate.put(
                    "http://localhost:5000/products/{id}",
                    product,
                    invoiceDetails.getIdProducto()
            );
            invoiceDetails.setPrice(product.getPrice() * invoiceDetails.getAmount());
            // Guardar el detalle de la factura
            return ResponseEntity.ok(invoiceDetailsServices.save(invoiceDetails));
        }catch(Exception error){
            error.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

    //Documentamos con Swagger el update
    @Operation(
            summary = "Modifica el invoice details solicitado por el usuario",
            description = "Modifica el invoice details solicitado por el usuario con lo que ingresas"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "✅ Invoice details actualizado exitosamente",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetails.class))}
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "❌ El invoice details no pudo ser actualizado",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetails.class))}
                    )
            }
    )
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InvoiceDetails> update(@PathVariable Long id, @RequestBody InvoiceDetails invoiceDetails) {
        System.out.println(invoiceDetails);
        try {
            InvoiceDetails invoiceDetailsActual = invoiceDetailsServices.findById(id);
            if (invoiceDetailsActual == null) return ResponseEntity.notFound().build();

            RestTemplate restTemplate = new RestTemplate();

            Long oldProductId = invoiceDetailsActual.getIdProducto();
            Long newProductId = invoiceDetails.getIdProducto();
            int oldAmount = invoiceDetailsActual.getAmount();
            int newAmount = invoiceDetails.getAmount() == 0 ?invoiceDetailsActual.getAmount() * invoiceDetails.getInvoiceIds().size() : invoiceDetails.getAmount() * invoiceDetails.getInvoiceIds().size();

            // Validaciones iniciales
            if (newAmount < 0 || newProductId == null) {
                return ResponseEntity.notFound().build();
            }
            System.out.println(newAmount);
            // Obtener producto viejo y nuevo
            Products oldProduct = restTemplate.getForObject("http://localhost:5000/products/{id}", Products.class, oldProductId);
            Products newProduct = oldProductId.equals(newProductId)
                    ? oldProduct
                    : restTemplate.getForObject("http://localhost:5000/products/{id}", Products.class, newProductId);

            if (oldProduct == null || newProduct == null) {
                return ResponseEntity.badRequest().build();
            }
            for (Long invoiceId : invoiceDetails.getInvoiceIds()) {
                if(!invoiceDetailsActual.getInvoiceIds().contains(invoiceId)) {
                    invoiceDetailsActual.getInvoiceIds().add(invoiceId);
                }
            }
            int unidadPorFactua = invoiceDetails.getAmount() == 0 ?
                    invoiceDetails.getAmount():
                    invoiceDetailsActual.getAmount();

            newAmount = unidadPorFactua * invoiceDetailsActual.getInvoiceIds().size();

            // Restablecer stock del producto viejo
            oldProduct.setStock(oldProduct.getStock() + oldAmount);
            oldProduct.getInvoiceDetailsIds().remove(invoiceDetailsActual.getId());

            restTemplate.put("http://localhost:5000/products/{id}", oldProduct, oldProductId);

            // Verificar stock disponible en nuevo producto
            if (newProduct.getStock() < newAmount) {
                return ResponseEntity.badRequest().build();
            }

            // Aplicar nuevo stock
            newProduct.setStock(newProduct.getStock() - newAmount );
            newProduct.getInvoiceDetailsIds().add(invoiceDetailsActual.getId());

            restTemplate.put("http://localhost:5000/products/{id}", newProduct, newProductId);

            // Actualizar el InvoiceDetails
            invoiceDetailsActual.setAmount(newAmount);
            invoiceDetailsActual.setIdProducto(newProductId);



            return ResponseEntity.ok(invoiceDetailsServices.update(id, invoiceDetailsActual));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    //Documentamos con Swagger el delete
    @Operation(
            summary = "Se elimina el invoice details solicitado por el usuario",
            description = "Elimina el invoice que se encuentra con el id ingresado por el usuario"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Invoice details eliminado exitosamente.",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = InvoiceDetails.class)
                    )}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<InvoiceDetails> delete(@PathVariable Long id) {
        InvoiceDetails invoiceDetails = invoiceDetailsServices.findById(id);
        if (invoiceDetails == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            Products product = restTemplate.getForObject(
                    "http://localhost:5000/products/{id}",
                    Products.class,
                    invoiceDetails.getIdProducto()
            );

            if (product == null) {
                return ResponseEntity.badRequest().build();
            }

            // Actualiza el stock
            product.setStock(product.getStock() + invoiceDetails.getAmount());

            // Asegura mantener campos necesarios
            product.setPrice(product.getPrice());
            product.setCode(product.getCode());
            product.setDescription(product.getDescription());

            // Quita el ID del InvoiceDetails del producto
            product.getInvoiceDetailsIds().remove(invoiceDetails.getId());

            // Actualiza el producto
            restTemplate.put(
                    "http://localhost:5000/products/{id}",
                    product,
                    invoiceDetails.getIdProducto()
            );
            if(invoiceDetails.getInvoiceIds().size()==0){
                return ResponseEntity.ok(invoiceDetailsServices.delete(id));
            }
            for (Long invoiceId : invoiceDetails.getInvoiceIds()) {
                Invoice invoice = restTemplate.getForObject(
                        "http://localhost:5000/invoices/{id}",
                        Invoice.class,
                        invoiceId
                );
                invoice.getIdInvoiceDetails().remove(invoiceDetails.getId());
                invoice.setIdDelCliente(invoice.getIdDelCliente());
                System.out.println(invoice.getIdInvoiceDetails());
                String url = "http://localhost:5000/invoices/{id}?deleting={deleting}";
                Map<String,Object> params = new HashMap<>();
                params.put("id",invoiceId);
                params.put("invoice",invoice);
                params.put("deleting",true);
                restTemplate.put(url,invoice,invoiceId,params);
            }
            return ResponseEntity.ok(invoiceDetailsServices.delete(id));

        } catch (Exception error) {
            error.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
