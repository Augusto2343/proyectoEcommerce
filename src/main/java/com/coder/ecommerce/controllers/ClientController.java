package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.services.ClientServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientServices clientServices;
    @GetMapping
    public ResponseEntity<List<Client>> findAll(){
        return ResponseEntity.ok(this.clientServices.findAll());
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> save(@RequestBody Client client){
        try{
            Client newClient = this.clientServices.save(client);
            return ResponseEntity.created(URI.create("/clients/" + newClient.getId())).body(newClient);
        }catch(Exception error){
            return ResponseEntity.badRequest().build();
        }

    }
    @PutMapping(value="/{id}", consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Client>> update(@PathVariable Long id,@RequestBody Client client){
        Optional<Client> clientOptional = this.clientServices.update(id, client);
        if(clientOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientOptional);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Client>> delete(@PathVariable Long id){
        Optional<Client> clientOptional= this.clientServices.delete(id);
        if(clientOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientOptional);
    }
}
