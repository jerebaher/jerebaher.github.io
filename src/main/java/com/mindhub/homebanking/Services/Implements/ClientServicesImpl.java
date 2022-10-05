package com.mindhub.homebanking.Services.Implements;

import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientServicesImpl implements ClientService {

    @Autowired
    ClientRepositories clientRepositories;

    @Override
    public List<Client> findAllClients() {
        return clientRepositories.findAll();
    }
    @Override
    public Client findClientById(Long id){
        return clientRepositories.findById(id).get();
    }

    @Override
    public Client findClientByEmail(String email){
        return clientRepositories.findByEmail(email);
    }

    @Override
    public void saveClient(Client client){
        clientRepositories.save(client);
    }

    @Override
    public void deleteClient(Client client){
        clientRepositories.delete(client);
    }
}
