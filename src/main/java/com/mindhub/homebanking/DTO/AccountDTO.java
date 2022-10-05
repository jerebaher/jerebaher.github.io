package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private String numberAccount;
    private LocalDateTime creationDate;
    private double balance;
    private long id;
    private Set<TransactionDTO> transactions;
    private Set<CardDTO> cards;

    public AccountDTO() {}

    public AccountDTO(Account account){
        this.numberAccount = account.getNumberAccount();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.id = account.getId();
        this.transactions = account.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toSet());
        this.cards = account.getCard().stream().map(CardDTO::new).collect(Collectors.toSet());
    }

    public String getNumberAccount() {
        return numberAccount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public long getId() {
        return id;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public Set<CardDTO> getCard() {
        return cards;
    }
}
