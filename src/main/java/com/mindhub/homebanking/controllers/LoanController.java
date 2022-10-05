package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.Services.*;
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
public class LoanController {

    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;
    @Autowired
    LoanService loanService;
    @Autowired
    ClientLoanService clientLoanService;
    @Autowired
    TransactionService transactionService;

    @GetMapping("/loans")
    public Set<LoanDTO> getLoans(){
        return loanService.findAllLoans().stream().map(LoanDTO::new).collect(Collectors.toSet());
    }

    @GetMapping("/loans/{id}")
    public LoanDTO getLoanById (@PathVariable long id){
        return new LoanDTO(loanService.findLoanById(id));
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createLoan(@RequestBody LoanApplicationDTO loanApplication,
                                             Authentication authentication){

        Client authClient = clientService.findClientByEmail(authentication.getName());

        Account account = accountService.findByNumber(loanApplication.getTargetAccount());

        Loan loan = loanService.findLoanById(loanApplication.getId());

        Set<Loan> loans = authClient.getClientLoan().stream().map(ClientLoan::getLoan).collect(Collectors.toSet());

        if (loanApplication.getAmount() <= 0 || loanApplication.getPayments() <= 0 || loanApplication.getTargetAccount().isEmpty()){
            return new ResponseEntity<>("Tienes un campo " +
                    "vacio. Por favor, revisa los datos.",
                    HttpStatus.FORBIDDEN);
        }
        if (loan == null){
            return new ResponseEntity<>("El prestamo que desea solicitar, no existe.",
                    HttpStatus.FORBIDDEN);
        }
        if (loanApplication.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>("El monto " +
                    "deseado es mayor al monto maximo del prestamo solicitado. " +
                    "Por favor, solicite otro monto.", HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayments().contains(loanApplication.getPayments())){
            return new ResponseEntity<>("La cantidad de " +
                    "cuotas solicitada, no está disponible." +
                    " Por favor, solicite otra cantidad de cuotas.",
                    HttpStatus.FORBIDDEN);
        }
        if (account == null){
            return new ResponseEntity<>("La cuenta de " +
                    "destino no existe. Por favor, " +
                    "verifica el numero de cuenta de destino.", HttpStatus.FORBIDDEN);
        }
        if (!authClient.getAccounts().contains(account)){
            return new ResponseEntity<>("No se ha podido " +
                    "autenticar la cuenta de destino. Por" +
                    " favor, vuelva intentar con otro " +
                    "numero de cuenta.", HttpStatus.FORBIDDEN);
        }
        if (loans.contains(loan)){
            return new ResponseEntity<>("Ya tienes un " +
                    "prestamo de este tipo.", HttpStatus.FORBIDDEN);
        }

        ClientLoan newLoan = new ClientLoan(loanApplication.getAmount() * 1.2,
                loanApplication.getPayments(), authClient, loan);

        Transaction newTransaction = new Transaction(loanApplication.getAmount(),
                "Loan approved "+loan.getName(),
                LocalDateTime.now(), TransactionType.CREDITO,
                account.getBalance() + loanApplication.getAmount());
        account.addTransaction(newTransaction);

        transactionService.saveTransaction(newTransaction);
        clientLoanService.saveClientLoan(newLoan);
        account.setBalance(account.getBalance()+loanApplication.getAmount());

        return new ResponseEntity<>("Prestamo aprobado", HttpStatus.CREATED);
    }

}
