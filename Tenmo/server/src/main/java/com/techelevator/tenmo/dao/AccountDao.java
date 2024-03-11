package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.BalanceDTO;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    public BalanceDTO getBalance(String username);

    Account getAccountByUserId(int userId);

    Account getAccountAccountId(int accountId);

    boolean update(Account accountToUpdate);



}


