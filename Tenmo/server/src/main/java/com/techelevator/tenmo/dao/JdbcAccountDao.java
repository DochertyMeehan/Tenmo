package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.BalanceDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public BalanceDTO getBalance(String username) {
        String sql = "SELECT username, balance\n" +
                "FROM account\n" +
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id\n" +
                "WHERE username = ?;";

        BalanceDTO balanceDTO = null;

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
            if(results.next()) {
                String user = results.getString("username");
                double balance = results.getDouble("balance");

                balanceDTO = new BalanceDTO();
                balanceDTO.setUsername(user);
                balanceDTO.setBalance(balance);

            }
        } catch (DataAccessException e) {

        }
        return balanceDTO;
    }

    @Override
    public Account getAccountByUserId(int userId) {
        String sql = "SELECT * FROM account WHERE user_id = ?;";

        Account account = null;

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if(results.next()) {
                account = new Account();
                account.setAccountId(results.getInt("account_id"));
                account.setBalance(results.getDouble("balance"));
                account.setUserId(userId);

            }
        } catch (DataAccessException e) {

        }
        return account;
    }

    @Override
    public Account getAccountAccountId(int accountId) {
        String sql = "SELECT * FROM account WHERE account_id = ?;";

        Account account = null;

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
            if(results.next()) {
                account = new Account();
                account.setAccountId(accountId);
                account.setBalance(results.getDouble("balance"));
                account.setUserId(results.getInt("user_id"));

            }
        } catch (DataAccessException e) {

        }
        return account;
    }



    @Override
    public boolean update(Account accountToUpdate) {
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?;";
        try {
            jdbcTemplate.update(sql, accountToUpdate.getBalance(), accountToUpdate.getAccountId());
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }



}
