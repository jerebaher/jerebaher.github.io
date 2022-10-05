package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name="tarjetas_deshabilitadas")
public class DisabledCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
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

    public DisabledCard() {
    }

    public DisabledCard(Card card, Boolean active) {
        this.cardHolder = card.getCardHolder();
        this.cardType = card.getCardType();
        this.cardColor = card.getCardColor();
        this.numberCard = card.getNumberCard();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
        this.client = card.getClient();
    }

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

}
