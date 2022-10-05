package com.mindhub.homebanking.Services.Implements;

import com.mindhub.homebanking.Services.ClientLoanService;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.ClientLoanRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ClientLoanServicesImpl implements ClientLoanService {

    @Autowired
    ClientLoanRepositories clientLoanRepositories;

    @Override
    public Set<ClientLoan> findAllClientLoans() {
        return new HashSet<>(clientLoanRepositories.findAll());
    }

    @Override
    public ClientLoan findClientLoanById(Long id){
        return clientLoanRepositories.findById(id).orElse(null);
    }

    @Override
    public void saveClientLoan(ClientLoan clientLoan){
        clientLoanRepositories.save(clientLoan);
    }
}
