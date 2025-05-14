package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.entities.Invoice;
import com.coder.ecommerce.services.ClientServices;

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
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientServices clientServices;
    @GetMapping
    public ResponseEntity<List<Client>>findAll(){
        return ResponseEntity.ok(clientServices.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Client> findById (@PathVariable Long id){
        return ResponseEntity.ok(clientServices.findById(id));
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> save(@RequestBody Client client){
        try{
            return ResponseEntity.ok(clientServices.save(client));
        }catch(Exception error){
            return ResponseEntity.badRequest().build();
        }

    }
    @PutMapping(value="/{id}", consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client client){
        Client clientToUpdate =clientServices.update(id, client);
        if(clientToUpdate ==null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(clientToUpdate);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Client> delete(@PathVariable Long id){
        Client clientToDelete= clientServices.delete(id);
        if(clientToDelete== null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientToDelete);
    }
}
