package com.mindhub.homebanking.DTO;

public class LoanApplicationDTO {

    private long id;
    private double amount;
    private int payments;
    private String targetAccount;

    LoanApplicationDTO(){}

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getTargetAccount() {
        return targetAccount;
    }
}
