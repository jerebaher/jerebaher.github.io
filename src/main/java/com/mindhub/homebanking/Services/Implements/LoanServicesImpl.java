package com.mindhub.homebanking.Services.Implements;

import com.mindhub.homebanking.Services.LoanService;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class LoanServicesImpl implements LoanService {

    @Autowired
    LoanRepositories loanRepositories;

    @Override
    public Set<Loan> findAllLoans() {
        return new HashSet<>(loanRepositories.findAll());
    }

    @Override
    public Loan findLoanById(Long id){
        return loanRepositories.findById(id).get();
    }

    @Override
    public void saveLoan(Loan loan){
        loanRepositories.save(loan);
    }
}
