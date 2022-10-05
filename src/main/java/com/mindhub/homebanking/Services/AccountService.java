package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Account;

import java.util.Set;

public interface AccountService {

    public Account findByNumber(String number);

    public void saveAccount(Account account);

    void deleteAccount(Account account);

    public Account findAccountById(long id);

    public Set<Account> findAllAccounts();
}
