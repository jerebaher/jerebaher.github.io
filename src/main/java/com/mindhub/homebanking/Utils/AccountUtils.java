package com.mindhub.homebanking.Utils;

import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.models.Account;
import org.springframework.beans.factory.annotation.Autowired;

public final class AccountUtils {

    @Autowired
    AccountService accountService;

    private AccountUtils(){}

    /*public static int getRandomNumber() {
        return (int) ((Math.random() * (99999999 - 10000000)) + 10000000);
    }

    public static String createNumberAccount(){

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
    }*/
}
