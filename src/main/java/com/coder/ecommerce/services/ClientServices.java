package com.coder.ecommerce.services;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.repositories.ClientRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServices {
    @Autowired
    private ClientRepositories clientsRepository;
    public Client save(Client client){
        return this.clientsRepository.save(client);
    }
    public List<Client> findAll(){return this.clientsRepository.findAll();}
    public Optional<Client> update(Long id, Client client){
        Optional<Client> clientOptional = this.clientsRepository.findById(id);
        if(clientOptional.isEmpty()){
            return Optional.empty();
        }
        clientOptional.get().setName(client.getName());
        clientOptional.get().setLastname(client.getLastname());
        clientOptional.get().setDocnumber(client.getDocnumber());
        this.clientsRepository.save(clientOptional.get());
        return clientOptional;

    }
    public Optional<Client> delete(Long id){
        Optional<Client> clientOptional = this.clientsRepository.findById(id);
        if(clientOptional.isEmpty()){
            return Optional.empty();
        }
        this.clientsRepository.delete(clientOptional.get());

        return clientOptional;
    }
}
