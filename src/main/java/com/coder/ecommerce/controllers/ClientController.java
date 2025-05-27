package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.entities.Invoice;
import com.coder.ecommerce.entities.Products;
import com.coder.ecommerce.services.ClientServices;

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
@Tag(name="Endpoint clientes", description = "CRUD de clientes de la tienda")

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientServices clientServices;
    //Documentamos con Swagger el findAll
    @Operation(
            summary = "Listamos los clientes que existen",
            description = "Devuelve una lista de los clientes que existen"
    )
    @ApiResponse(
            responseCode = "200",
            description = "✅Devuelve una lista de los clientes creados",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Client.class)
                    )}
    )

    @GetMapping
    public ResponseEntity<List<Client>>findAll(){

        return ResponseEntity.ok(clientServices.findAll());
    }
    //Documentamos con Swagger el findById
    @Operation(
            summary = "Devuelve el cliente solicitado por el usuario",
            description = "Devuelve el cliente solicitado por el usuario"
    )
    @ApiResponse(
            responseCode = "200",
            description = "✅Cliente solicitado devuelto exitosamente",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Client.class)
                    )}
    )

    @GetMapping("/{id}")
    public ResponseEntity<Client> findById (@PathVariable Long id){
        return ResponseEntity.ok(clientServices.findById(id));
    }

    //Documentamos con Swagger el save
    @Operation(
            summary = "Guarda un nuevo cliente",
            description = "Se guarda un nuevo cliente en la tienda"
    )
    @ApiResponses(
            value={
                    @ApiResponse(
                            responseCode = "200",
                            description = "✅ Cliente creado exitosamente",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))}
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "❌ El cliente no pudo ser creado",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))}
                    )
            }
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> save(@RequestBody Client client){
        try{
            return ResponseEntity.ok(clientServices.save(client));
        }catch(Exception error){
            return ResponseEntity.badRequest().build();
        }

    }

    //Documentamos con Swagger el update
    @Operation(
            summary = "Guarda un nuevo cliente",
            description = "Se guarda un nuevo cliente en la tienda"
    )
    @ApiResponses(
            value={
                    @ApiResponse(
                            responseCode = "200",
                            description = "✅ Cliente modificado exitosamente",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))}
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "❌ El cliente no pudo ser modificado",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))}
                    )
            }
    )
    @PutMapping(value="/{id}", consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client client){
        Client clientToUpdate =clientServices.update(id, client);
        if(clientToUpdate ==null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(clientToUpdate);
    }
    //Documentamos con Swagger el findById
    @Operation(
            summary = "Elimina el cliente solicitado por el usuario",
            description = "Elimina el cliente solicitado por el usuario"
    )
    @ApiResponse(
            responseCode = "200",
            description = "✅Cliente eliminado exitosamente",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Client.class)
                    )}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Client> delete(@PathVariable Long id){
        Client clientToDelete= clientServices.delete(id);
        if(clientToDelete== null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientToDelete);
    }
}
