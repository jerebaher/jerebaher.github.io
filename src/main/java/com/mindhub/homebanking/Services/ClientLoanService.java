package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.ClientLoan;

import java.util.Optional;
import java.util.Set;

public interface ClientLoanService {
    Set<ClientLoan> findAllClientLoans();

    ClientLoan findClientLoanById(Long id);

    void saveClientLoan(ClientLoan clientLoan);
}
