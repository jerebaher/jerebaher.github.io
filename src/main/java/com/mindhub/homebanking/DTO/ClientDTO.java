package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Authority;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.DisabledCard;
import net.minidev.json.annotate.JsonIgnore;


import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO{
    private String name, lastName, email;
    private long id;
    private Set<AccountDTO> accounts;
    private Set<ClientLoanDTO> clientLoans;
    private Set<CardDTO> cards;
    private Authority authority;

    private Set<DisabledCard> disabledCards;

    public ClientDTO(){}
    public ClientDTO(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts =
                client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toSet());
        this.clientLoans =
                client.getClientLoan().stream().map(ClientLoanDTO::new).collect(Collectors.toSet()); //Loans
        this.cards =
                client.getCards().stream().map(CardDTO::new).collect(Collectors.toSet());
        this.authority = client.getAuthority();
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public long getId() {
        return id;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return clientLoans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }
}
