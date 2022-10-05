package com.mindhub.homebanking.Services.Implements;

import com.mindhub.homebanking.Services.TransactionService;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionsRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TransactionServicesImpl implements TransactionService {

    @Autowired
    private TransactionsRepositories transactionsRepositories;

    @Override
    public Set<Transaction> findAllTransactions() {
        return new HashSet<>(transactionsRepositories.findAll());
    }

    @Override
    public Transaction findTransactionById(Long id){
        return transactionsRepositories.findById(id).orElse(null);
    }

    @Override
    public void saveTransaction(Transaction transaction){
        transactionsRepositories.save(transaction);
    }

    @Override
    public void deleteTransaction(Transaction transaction){
        transactionsRepositories.delete(transaction);
    }
}
