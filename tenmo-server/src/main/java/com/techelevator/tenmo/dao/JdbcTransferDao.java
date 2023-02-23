package com.techelevator.tenmo.dao;
//adding words to push
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class JdbcTransferDao implements TransferDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> getTransfersByAccountId(int accountId) {
        return null;
    }

    @Override
    public Transfer getTransfersByTransferId(int transferId) {
        return null;
    }
}
