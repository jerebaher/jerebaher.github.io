package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="cuenta")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String numberAccount;
    private LocalDateTime creationDate;
    private double balance;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Card> cards;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    public Account() {}

    public Account(String numberAccount,
                   LocalDateTime creationDate,
                   double balance) {
        this.numberAccount = numberAccount;
        this.creationDate = creationDate;
        this.balance = balance;
    }


    //*GETTERS

    public long getId() {
        return id;
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

    public Set<Card> getCard() {
        return cards;
    }

    public Client getClient() {
        return client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    //*SETTERS

    public void setNumberAccount(String numberAccount) {
        this.numberAccount = numberAccount;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCard(Set<Card> cards) {
        this.cards = cards;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        transactions.add(transaction);
    }
}

