package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class JdbcAccountDao implements AccountDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal viewCurrentBalanceByUserId() {
        return null;
    }

    @Override
    public BigDecimal viewCurrentBalanceByAccountId() {
        return null;
    }
}
