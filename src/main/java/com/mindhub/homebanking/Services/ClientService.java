package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Client;
import java.util.List;

public interface ClientService {

    public List<Client> findAllClients();

    Client findClientById(Long id);

    Client findClientByEmail(String email);

    void saveClient(Client client);

    void deleteClient(Client client);
}
