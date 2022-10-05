package com.mindhub.homebanking;

import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {

    @Autowired
    LoanRepositories loanRepositories;

    @Autowired
    ClientRepositories clientRepositories;

    @Autowired
    AccountsRepositories accountsRepositories;

    @Autowired
    TransactionsRepositories transactionsRepositories;

    @Autowired
    CardRepositories cardRepositories;

    @Autowired
    static
    AccountService accountService;

    @Test
    public void existLoans(){
        List<Loan> loans = loanRepositories.findAll();
        assertThat(loans, is(not(empty())));
    }
    @Test
    public void existClients(){
        List<Client> clients = clientRepositories.findAll();
        assertThat(clients, is(not(empty())));
    }
    @Test
    public void existAccounts(){
        List<Account> accounts = accountsRepositories.findAll();
        assertThat(accounts, is(not(empty())));
    }
    /*@Test
    public void existTransactions(){
        List<Transaction> transactions = transactionsRepositories.findAll();
        assertThat(transactions, is(not(empty())));
    }*/



    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepositories.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal Loan"))));
    }
    @Test
    public void existMortgageLoan(){
        List<Loan> loans = loanRepositories.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Mortgage Loan"))));
    }
    @Test
    public void existAutomotiveLoan(){
        List<Loan> loans = loanRepositories.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Automotive Loan"))));
    }
    @Test
    public void existMelba(){
        Client client = clientRepositories.findByEmail("melbalorenzo@gmail.com");
        assertThat(client, hasProperty("name", is("Melba")));
    }
    @Test
    public void existAccount(){
        Account account = accountsRepositories.findByNumberAccount("VIN001");
        assertThat(account, hasProperty("numberAccount", is("VIN001")));
    }
}
