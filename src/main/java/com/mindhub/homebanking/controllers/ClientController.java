package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.DTO.ClientLoanDTO;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Authority;
import com.mindhub.homebanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

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

    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String name,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password) {

        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Faltan datos en algunos de los campos.", HttpStatus.FORBIDDEN);
        }
        if (clientService.findClientByEmail(email) != null) {
            return new ResponseEntity<>("El correo electronico ya está en uso", HttpStatus.FORBIDDEN);
        }
        if (email.contains("@admin")){
            return new ResponseEntity<>("No podes registrarte como administrador.", HttpStatus.FORBIDDEN);
        }

        Account newAccount = new Account(createNumberAccount(), LocalDateTime.now(),0);
        accountService.saveAccount(newAccount);

        Client newClient = new Client(name, lastName, email, passwordEncoder.encode(password), Authority.CLIENT);
        newClient.addAccount(newAccount);
        clientService.saveClient(newClient);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @GetMapping("/clients")
    public List<ClientDTO> getClientDTOS(){
        return clientService.findAllClients().stream().map(ClientDTO::new).collect(Collectors.toList());
    };

    @GetMapping("/clients/{id}")
    public ClientDTO getClientById(@PathVariable Long id){
        return new ClientDTO(clientService.findClientById(id));
    };

    @GetMapping("/clients/current")
    public ClientDTO getAuthentication(Authentication authentication){
        return new ClientDTO(clientService.findClientByEmail(authentication.getName()));
    };

    @GetMapping("/clients/current/loans")
    public Set<ClientLoanDTO> getLoansAuthClient(Authentication authentication){
        Client authClient = clientService.findClientByEmail(authentication.getName());
        return authClient.getClientLoan().stream().map(ClientLoanDTO::new).collect(Collectors.toSet());
    }
}
