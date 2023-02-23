package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.dao.EmptyResultDataAccessException;
import com.techelevator.tenmo.model.Account;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {
    public class JdbcAccountDao implements AccountDao {
        private final JdbcTemplate jdbcTemplate;

        public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = null;

        String sql = "SELECT account_id, user_id, balance FROM account";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            accounts.add(mapRowToAccount(results));
        }

        return accounts;
    }

    @Override
    public Account getAccountByUserId(int userId) {
        Account account = null;

        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()) {
            account = mapRowToAccount(results);
        }

        return account;

        public List<Account> getAllAccounts() {
            List<Account> accounts = null;

            String sql = "SELECT account_id, user_id, balance FROM account";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                accounts.add(mapRowToAccount(results));
            }

            return accounts;
        }

    private Account mapRowToAccount(SqlRowSet results) {
        Account account = new Account();
        account.setAccountId(results.getInt("account_id"));
        account.setUserId(results.getInt("user_id"));
        account.setBalance(results.getBigDecimal("balance"));
        return account;

        @Override
        public Account getAccountByUserId(int userId) {
            Account account = null;

            String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                account = mapRowToAccount(results);
            }

            return account;
        }

        private Account mapRowToAccount(SqlRowSet results) {
            Account account = new Account();
            account.setAccountId(results.getInt("account_id"));
            account.setUserId(results.getInt("user_id"));
            account.setBalance(results.getBigDecimal("balance"));
            return account;
        }

}
