package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Transaction;

import java.util.Set;

public interface TransactionService {
    Set<Transaction> findAllTransactions();

    Transaction findTransactionById(Long id);

    void saveTransaction(Transaction transaction);

    void deleteTransaction(Transaction transaction);
}
