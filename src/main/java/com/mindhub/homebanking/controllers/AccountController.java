package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.Services.TransactionService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
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
public class AccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    ClientService clientService;
    @Autowired
    TransactionService transactionService;

    public int getRandomNumber() {
        return (int) ((Math.random() * (99999999 - 10000000)) + 10000000);
    }

    public String createNumberAccount(){

        String numberAccountCreated = "";
        boolean cond = true;
        while (cond){
            numberAccountCreated = "VIN-" + getRandomNumber();
            Account account = accountService.findByNumber(numberAccountCreated);
            if (account == null){
                cond = false;
            }
        }
        return numberAccountCreated;
    }

    @GetMapping("/accounts")
    public Set<AccountDTO> getAccountDTO(){
        return accountService.findAllAccounts().stream().map(AccountDTO::new).collect(Collectors.toSet());
    };

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccountById(@PathVariable Long id, Authentication authentication){

        Client authClient = clientService.findClientByEmail(authentication.getName());
        Account account = accountService.findAccountById(id);

        if(authClient.getAccounts().contains(account)) {
            return new AccountDTO(accountService.findAccountById(id));
        } else {
            return null;
        }
    };

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication){

        Client authClient =
                clientService.findClientByEmail(authentication.getName());

        if(authClient.getAccounts().size() > 3) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Account newAccount = new Account(createNumberAccount(),
                LocalDateTime.now(),0);
        authClient.addAccount(newAccount);
        accountService.saveAccount(newAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/clients/current/accounts")
    public ResponseEntity<Object> deleteAccount(@RequestParam String targetAccount, @RequestParam String deletedAccount,
                                                Authentication authentication){

        Client authClient = clientService.findClientByEmail(authentication.getName());
        Account originAccount = accountService.findByNumber(deletedAccount);
        Account destinyAccount = accountService.findByNumber(targetAccount);
        Set<Transaction> transactionSet;

        if (originAccount == null){
            return new ResponseEntity<>("La cuenta de origen no existe.", HttpStatus.FORBIDDEN);
        }
        if (destinyAccount == null){
            return new ResponseEntity<>("La cuenta de destino no existe.", HttpStatus.FORBIDDEN);
        }
        if (!authClient.getAccounts().contains(originAccount)){
            return new ResponseEntity<>("No puedes eliminar esta cuenta porque no te pertenece.", HttpStatus.FORBIDDEN);
        }

        destinyAccount.setBalance(originAccount.getBalance() + destinyAccount.getBalance());

        originAccount.getTransactions().forEach(transaction ->
                destinyAccount.addTransaction(new Transaction(transaction.getAmount(), transaction.getDescription(),
                        transaction.getDate(), transaction.getType(), transaction.getAccountBalance())));

        originAccount.getTransactions().forEach(transaction -> transactionService.deleteTransaction(transaction));

        accountService.deleteAccount(originAccount);
        accountService.saveAccount(destinyAccount);

        return new ResponseEntity<>("Haz eliminado tu cuenta con exito.", HttpStatus.OK);
    }
}
