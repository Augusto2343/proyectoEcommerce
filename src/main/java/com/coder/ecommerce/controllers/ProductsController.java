package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.InvoiceDetails;
import com.coder.ecommerce.entities.Products;
import com.coder.ecommerce.services.ProductServices;
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

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
//Documentamos con Swagger el endpoint
@Tag(name="Endpoint Productos", description = "CRUD de productos de la tienda")
@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductServices productService;

    //Documentamos con Swagger el findAll
    @Operation(
            summary = "Listamos los productos de la tienda",
            description = "Devuelve una lista de productos de la tienda"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Devuelve una lista de productos de la tienda",
            content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Products.class)
            )}
    )
    @GetMapping
    public ResponseEntity<List<Products>> findAll() {

        return ResponseEntity.ok(productService.findAll());
    }
    //Documentamos con Swagger el findById

    @Operation(
            summary = "Devolvemos el producto solicitado por el usuario",
            description = "Devuelve un producto de la tienda por id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Devuelve una lista de productos de la tienda",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Products.class)
                    )}
    )

    @GetMapping("/{id}")
    public ResponseEntity<Products> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }
    //Documentamos con Swagger el save

    @Operation(
            summary = "Guarda un producto nuevo",
            description = "Se guarda un producto nuevo en la tienda"
    )
    @ApiResponses(
            value={
                @ApiResponse(
                responseCode = "200",
                description = "✅ Producto creado exitosamente",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Products.class))}
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "❌ El producto no pudo ser creado",
                        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Products.class))}
                )
            }
    )

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Products> save(@RequestBody Products products) {
        return ResponseEntity.ok(productService.save(products));
    }

    @Operation(
            summary = "El usuario modifica el producto solicitado",
            description = "Modifica un producto ya creado"
    )
    @ApiResponses(
            value={
                    @ApiResponse(
                            responseCode = "200",
                            description = "✅ Producto modificado exitosamente",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Products.class))}
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "❌ El producto no pudo ser modificado",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Products.class))}
                    )
            }
    )
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Products> update(@PathVariable Long id, @RequestBody Products product) {

        try{
            Products productToUpdate = productService.findById(id);
            if(productToUpdate==null){
                return ResponseEntity.notFound().build();
            }

            productToUpdate.setStock((product.getStock() == 0 ? productToUpdate.getStock() : product.getStock()));
            productToUpdate.setDescription(product.getDescription() == null ? productToUpdate.getDescription() : product.getDescription() );
            productToUpdate.setCode(product.getCode() == null ? productToUpdate.getCode() : product.getCode() );
            productToUpdate.setPrice(product.getPrice() == 0 ? productToUpdate.getPrice() : product.getPrice() );
            return ResponseEntity.ok(productService.update(id, productToUpdate));
        }catch(Exception error){
            return ResponseEntity.badRequest().build();
        }

    }
    //Documentamos con Swagger el delete
    @Operation(
            summary = "Se elimina el producto solicitado por el usuario",
            description = "Elimina un producto de la tienda por id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "✅ Producto eliminado exitosamente",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Products.class))}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Products> delete(@PathVariable Long id) {
        Products product = productService.findById(id);

        try {
            if (!product.getInvoiceDetailsIds().isEmpty()) {
                RestTemplate restTemplate = new RestTemplate();
                for (Long invoiceDetailsId : product.getInvoiceDetailsIds()) {
                    restTemplate.delete(
                            "http://localhost:5000/invoice-details/{id}",
                            invoiceDetailsId
                    );
                }
                return ResponseEntity.ok(productService.delete(id));

            }
            return ResponseEntity.ok(productService.delete(id));


        } catch (Exception error) {
            return ResponseEntity.badRequest().build();
        }

    }
}
