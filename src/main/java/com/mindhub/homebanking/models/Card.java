package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name="tarjetas")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    private String cardHolder, numberCard;
    private CardType cardType;
    private CardColor cardColor;
    private int cvv;
    private LocalDate fromDate, thruDate;
    private double credit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    public Card() {
    }

    public Card(String cardHolder, String numberCard,
                CardType cardType, CardColor cardColor, int cvv,
                LocalDate fromDate, LocalDate thruDate,
                Client client, Account account) {
        this.cardHolder = cardHolder;
        this.cardType = cardType;
        this.cardColor = cardColor;
        this.numberCard = numberCard;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.client = client;
        this.account = account;
    }

    public Card(String cardHolder, String numberCard,
                CardType cardType, CardColor cardColor, int cvv,
                LocalDate fromDate, LocalDate thruDate,double credit,
                Client client) {
        this.cardHolder = cardHolder;
        this.cardType = cardType;
        this.cardColor = cardColor;
        this.numberCard = numberCard;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.credit = credit;
        this.client = client;
    }

    //* GETTERS

    public long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public CardType getCardType() {
        return cardType;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public double getCredit() {
        return credit;
    }

    public Account getAccount() {
        return account;
    }

    //* SETTERS

    public void setClient(Client client) {
        this.client = client;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
