package com.coder.ecommerce.services;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.repositories.ClientRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientServices {
    @Autowired
    private ClientRepositories clientsRepositories;
    public Client save(Client client){
        return this.clientsRepositories.save(client);
    }
    public List<Client> findAll(){
        return this.clientsRepositories.findAll();
    }

    public Client findById(Long id){
        return clientsRepositories.findById(id).orElse(null);
    }
    public Client update(Long id, Client client){
        Client clientToUpdate = clientsRepositories.findById(id).orElse(null);
        if(clientToUpdate ==null){
            return null;
        }
        clientToUpdate.setName(clientToUpdate.getName());
        client.setLastname(clientToUpdate.getLastname());
        client.setDocnumber(clientToUpdate.getDocnumber());
        clientsRepositories.save(client);
        return clientToUpdate;

    }
    public Client delete(Long id) {
        Client clientToDelete = clientsRepositories.findById(id).orElse(null);
        if (clientToDelete==null) {
            return null;
        }
        clientsRepositories.delete(clientToDelete);

        return clientToDelete;
    }
}
