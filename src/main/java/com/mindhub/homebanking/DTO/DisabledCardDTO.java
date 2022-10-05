package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.DisabledCard;

import java.time.LocalDate;

public class DisabledCardDTO {

    private long id;
    private String cardHolder, numberCard;
    private CardType cardType;
    private CardColor cardColor;
    private int cvv;
    private LocalDate fromDate, thruDate;
    private Boolean active;

    public DisabledCardDTO() {
    }

    public DisabledCardDTO(DisabledCard disabledCard) {
        this.id = disabledCard.getId();
        this.cardHolder = disabledCard.getCardHolder();
        this.numberCard = disabledCard.getNumberCard();
        this.cardType = disabledCard.getCardType();
        this.cardColor = disabledCard.getCardColor();
        this.cvv = disabledCard.getCvv();
        this.fromDate = disabledCard.getFromDate();
        this.thruDate = disabledCard.getThruDate();
    }

    public long getId() {
        return id;
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

    public Boolean getActive() {
        return active;
    }
}
