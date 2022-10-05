package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Card;

import java.util.List;

public interface CardService {

    List<Card> findAllCards();

    Card findCardById(Long id);

    void saveCard(Card card);

    void deleteCard(Card card);

    Card findCardByNumber(String number);
}
