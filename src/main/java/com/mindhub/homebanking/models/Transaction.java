package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;



@Entity
@Table(name="transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    private double amount;
    private String description;
    private LocalDateTime date;
    private TransactionType type;
    private double accountBalance;

    public Transaction(){};

    public Transaction(double amount, String description, LocalDateTime date,
                       TransactionType type, double accountBalance){
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
        this.accountBalance = accountBalance;
    }

    public Transaction(double amount, String description, LocalDateTime date, TransactionType type) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
    }

    //* GETTERS

    public long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    //* SETTERS

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
}
