package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.Services.TransactionService;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @Autowired
    AccountService accountService;
    @Autowired
    ClientService clientService;
    @Autowired
    CardService cardService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> transfer (@RequestParam double amount,
                                            @RequestParam String desc,
                                            @RequestParam String origin,
                                            @RequestParam String target,
                                            Authentication authentication){

        Client authClient = clientService.findClientByEmail(authentication.getName());

        Account originAcc = accountService.findByNumber(origin);

        Account targetAcc = accountService.findByNumber(target);

        if (authClient.getAccounts().contains(originAcc)){
            if (amount<=0 || desc.isEmpty() || origin.isEmpty() || target.isEmpty()){
                return new ResponseEntity<>("Debes completar todos los campos para realizar " +
                        "la transferencia.", HttpStatus.FORBIDDEN);
            }
            if (origin.equals(target)){
                return new ResponseEntity<>("La cuenta de origen es la misma que " +
                        "la cuenta de destino. Por favor, elige una cuenta de " +
                        "origen o de destino diferente",
                        HttpStatus.FORBIDDEN);
            }
            if (originAcc == null || targetAcc == null){
                return new ResponseEntity<>("La cuenta de origen o la cuenta de destino " +
                        "no existen.",
                        HttpStatus.FORBIDDEN);
            }
            if (amount > originAcc.getBalance()){
                return new ResponseEntity<>("Fondos " +
                        "insuficientes.",
                        HttpStatus.FORBIDDEN);
            }
        }else{
            return new ResponseEntity<>("Esta cuenta no pertenece al usuario autenticado",
                    HttpStatus.FORBIDDEN);
        }

        Transaction debitTransaction =
                new Transaction(-amount,
                        desc + " " + originAcc.getNumberAccount() + " a " + targetAcc.getNumberAccount(),
                        LocalDateTime.now(), TransactionType.DEBITO,originAcc.getBalance() - amount);
        originAcc.addTransaction(debitTransaction);
        originAcc.setBalance(originAcc.getBalance() - amount);
        transactionService.saveTransaction(debitTransaction);

        Transaction creditTransaction = new Transaction(amount,desc + " " + targetAcc.getNumberAccount() + " a " + originAcc.getNumberAccount(),
                        LocalDateTime.now(), TransactionType.CREDITO,targetAcc.getBalance() + amount);
        targetAcc.addTransaction(creditTransaction);
        targetAcc.setBalance(targetAcc.getBalance()+amount);
        transactionService.saveTransaction(creditTransaction);

        return new ResponseEntity<>("Transaccion realizada con exito.", HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/pay")
    public ResponseEntity<Object> pay(@RequestParam double amount, @RequestParam String cardNumber){

        Card card = cardService.findCardByNumber(cardNumber);

            if (amount <= 0 || cardNumber.isEmpty()){
                return new ResponseEntity<>("Debes completar todos los campos para realizar la transferencia.", HttpStatus.FORBIDDEN);
            }
            if (card.getCardType() == CardType.DEBITO){
                if (card.getAccount() == null){
                    return new ResponseEntity<>("La cuenta de origen no existe.", HttpStatus.FORBIDDEN);
                }
                if (amount > card.getAccount().getBalance()){
                    return new ResponseEntity<>("Fondos insuficientes.", HttpStatus.FORBIDDEN);
                }

                Transaction debitTransaction =
                        new Transaction(-amount, "Compra", LocalDateTime.now(),
                                TransactionType.DEBITO,card.getAccount().getBalance() - amount);

                card.getAccount().addTransaction(debitTransaction);
                card.getAccount().setBalance(card.getAccount().getBalance() - amount);
                transactionService.saveTransaction(debitTransaction);

            }else {
                if (amount > card.getCredit()){
                    return new ResponseEntity<>("No posee saldo suficiente para realizar la compra.", HttpStatus.FORBIDDEN);
                }

                Transaction debitTransaction =
                        new Transaction(-amount, "Compra a credito", LocalDateTime.now(), TransactionType.DEBITO);
                transactionService.saveTransaction(debitTransaction);
            }

        return new ResponseEntity<>("Transaccion realizada con exito.", HttpStatus.CREATED);
    }

    @GetMapping("/accounts/transactions")
    public Set<TransactionDTO> getTransactionDTO(){
        return transactionService.findAllTransactions().stream().map(TransactionDTO::new).collect(Collectors.toSet());
    };

    @GetMapping("/transactions/{id}")
    public TransactionDTO getTransactionById(@PathVariable Long id){
        return new TransactionDTO(transactionService.findTransactionById(id));
    };

}
