package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Loan;

import java.util.List;
import java.util.Set;

public interface LoanService {
    Set<Loan> findAllLoans();

    Loan findLoanById(Long id);

    void saveLoan(Loan loan);
}
