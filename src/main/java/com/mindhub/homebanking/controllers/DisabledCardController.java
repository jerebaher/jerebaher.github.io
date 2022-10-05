package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.DTO.DisabledCardDTO;
import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.Services.DisabledCardService;
import com.mindhub.homebanking.models.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DisabledCardController {

    @Autowired
    CardService cardService;

    @Autowired
    DisabledCardService disabledCardService;

    @GetMapping("/disabledCards")
    public Set<DisabledCardDTO> getDisabledCardDTO(){
        return disabledCardService.findAllDisabledCards().stream().map(DisabledCardDTO::new).collect(Collectors.toSet());
    }

    @GetMapping("/disabledCards/{id}")
    public DisabledCardDTO getCardsById(@PathVariable Long id){
        return new DisabledCardDTO(disabledCardService.findDisabledCardById(id));
    }

    /*@DeleteMapping("/disabledCards/{id}")
    public ResponseEntity<Object> enableCard(@PathVariable Long id){

        Card card = cardService.findCardById(id);
    }*/

}
