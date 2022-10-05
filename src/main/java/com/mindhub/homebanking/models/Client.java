package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="cliente")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String name, lastName, email, password;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards;

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoan;

    @OneToMany(mappedBy = "client", fetch=FetchType.EAGER)
    private Set<DisabledCard> disabledCards;

    private Authority authority;

    public Client(){}

    public Client(String name, String lastName,
                  String email, String password, Authority authority){
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.authority = authority;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Set<ClientLoan> getClientLoan() {
        return clientLoan;
    }

    public void setClientLoan(Set<ClientLoan> clientLoan) {
        this.clientLoan = clientLoan;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public Set<DisabledCard> getDisabledCards() {
        return disabledCards;
    }

    public void setDisabledCards(Set<DisabledCard> disabledCards) {
        this.disabledCards = disabledCards;
    }

    public void addAccount(Account account) {
        account.setClient(this);
        accounts.add(account);
    }

}

