package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.Services.DisabledCardService;
import com.mindhub.homebanking.Utils.CardUtils;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    AccountService accountService;
    @Autowired
    ClientService clientService;

    @Autowired
    CardService cardService;

    @Autowired
    DisabledCardService disabledCardService;

    @PostMapping("/clients/current/cards")
        public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam String numberAccount,
                                             @RequestParam CardColor cardColor, Authentication authentication) {

        Account account = accountService.findByNumber(numberAccount);
        Client authClient = clientService.findClientByEmail(authentication.getName());
        List<Card> listCard = authClient.getCards().stream()
                .filter(card -> card.getCardColor().equals(cardColor) && card.getCardType().equals(cardType))
                .collect(Collectors.toList());


        if (account == null) {
            return new ResponseEntity<>("La cuenta seleccionada no existe.", HttpStatus.FORBIDDEN);
        }
        if (account.getCard().size() >= 1){
            return new ResponseEntity<>("Ya tienes una tarjeta asociada a esta cuenta", HttpStatus.FORBIDDEN);
        }
        if (listCard.size() > 0) {
            return new ResponseEntity<>("No puedes tener 2 tarjetas del mismo tipo y color.",
                    HttpStatus.FORBIDDEN);
        }

        Card newCard = new Card();

       if (newCard.getCardType() == CardType.CREDITO){
           newCard.setCardHolder(authClient.getName()+" "+authClient.getLastName());
           newCard.setNumberCard(CardUtils.randomNumber()+"-"+CardUtils.randomNumber()
                   +"-"+CardUtils.randomNumber()+"-"+CardUtils.randomNumber());
           newCard.setCardType(cardType);
           newCard.setCardColor(cardColor);
           newCard.setCvv(CardUtils.getCvv());
           newCard.setFromDate(LocalDate.now());
           newCard.setThruDate(LocalDate.now().plusYears(5));
           newCard.setClient(authClient);
           cardService.saveCard(newCard);
       }else{
           newCard.setCardHolder(authClient.getName()+" "+authClient.getLastName());
           newCard.setNumberCard(CardUtils.randomNumber()+"-"+CardUtils.randomNumber()
                   +"-"+CardUtils.randomNumber()+"-"+CardUtils.randomNumber());
           newCard.setCardType(cardType);
           newCard.setCardColor(cardColor);
           newCard.setCvv(CardUtils.getCvv());
           newCard.setFromDate(LocalDate.now());
           newCard.setThruDate(LocalDate.now().plusYears(5));
           newCard.setClient(authClient);
           newCard.setAccount(account);
           cardService.saveCard(newCard);
       }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/cards/{id}")
    public ResponseEntity<Object> disabledCard(@PathVariable Long id){

        Card card = cardService.findCardById(id);

        if (card == null){
            return new ResponseEntity<>("Tu tarjeta ya está deshabilitada", HttpStatus.FORBIDDEN);
        }

        DisabledCard disabledCard = new DisabledCard(card,false);
        disabledCardService.saveDisabledCard(disabledCard);
        cardService.deleteCard(card);

        return new ResponseEntity<>("Tu tarjeta ha sido deshabilitada con exito.", HttpStatus.ACCEPTED);
    }

    @GetMapping("/cards")
    public Set<CardDTO> getCardDTO(){
        return cardService.findAllCards().stream().map(CardDTO::new).collect(Collectors.toSet());
    }

    @GetMapping("/cards/{id}")
    public CardDTO getCardsById(@PathVariable Long id){
        return new CardDTO(cardService.findCardById(id));
    }
}
