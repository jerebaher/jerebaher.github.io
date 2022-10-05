package com.mindhub.homebanking.Services.Implements;

import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServicesImpl implements CardService {

    @Autowired
    CardRepositories cardRepositories;

    @Override
    public List<Card> findAllCards() {
        return cardRepositories.findAll();
    }

    @Override
    public Card findCardById(Long id){
        return cardRepositories.findById(id).get();
    }

    @Override
    public void saveCard(Card card){
        cardRepositories.save(card);
    }

    @Override
    public void deleteCard(Card card){
        cardRepositories.delete(card);
    }

    @Override
    public Card findCardByNumber(String number){
        return cardRepositories.findByNumberCard(number);
    }
}
