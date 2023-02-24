package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    @Override
    public List<Transfer> getTransfersByPendingStatus(int currentUserId) {
        List<Transfer> pendingTransfers = new ArrayList<>();
        String sql = "SELECT t.transfer_id, tt.transfer_type_desc, ts.transfer_status_desc, t.account_from, t.account_to, t.amount " +
                "FROM transfer t " +
                "JOIN transfer_type tt USING(transfer_type_id) " +
                "JOIN transfer_status ts USING(transfer_status_id) " +
                "WHERE t.account_from IN (" +
                        "SELECT account_id " +
                        "FROM account " +
                        "WHERE user_id = ? " +
                "AND t.transfer_status_id = (" +
                        "SELECT transfer_status_id " +
                        "FROM transfer_status " +
                        "WHERE transfer_status_desc = 'Pending');";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, currentUserId);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            pendingTransfers.add(transfer);
        }

        return pendingTransfers;
    }

    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setId(rs.getInt("transfer_id"));
        transfer.setTransferType(rs.getString("transfer_type_desc"));
        transfer.setTransferStatus(rs.getString("transfer_status_desc"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        return transfer;
    }

}
