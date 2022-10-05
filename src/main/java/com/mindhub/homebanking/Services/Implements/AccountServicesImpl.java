package com.mindhub.homebanking.Services.Implements;

import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountsRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AccountServicesImpl implements AccountService {

    @Autowired
    AccountsRepositories accountsRepositories;

    @Override
    public Account findByNumber(String number){
        return accountsRepositories.findByNumberAccount(number);
    }

    @Override
    public void saveAccount(Account account){
        accountsRepositories.save(account);
    }

    @Override
    public void deleteAccount(Account account){
        accountsRepositories.delete(account);
    }

    @Override
    public Account findAccountById(long id){
        return accountsRepositories.findById(id).get();
    }

    @Override
    public Set<Account> findAllAccounts(){
        return new HashSet<>(accountsRepositories.findAll());
    }
}
