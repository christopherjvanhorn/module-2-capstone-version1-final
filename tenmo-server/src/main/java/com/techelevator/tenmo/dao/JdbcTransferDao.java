package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferRequestDto;
import org.springframework.dao.DataIntegrityViolationException;
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
    public List<Transfer> getTransfersByUserId(int accountId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT t.transfer_id, tt.transfer_type_desc, ts.transfer_status_desc, t.account_from, t.account_to, t.amount " +
        "FROM transfer t " +
                "JOIN transfer_type tt USING(transfer_type_id) " +
                "JOIN transfer_status ts USING(transfer_status_id) " +
                "JOIN account a ON a.account_id = t.account_from OR a.account_id = t.account_to " +
                "WHERE a.user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }

        return transfers;
    }

    @Override
    public Transfer getTransfersByTransferId(int transferId) {
        Transfer transfers = new Transfer();
        String sql = "SELECT t.transfer_id, tt.transfer_type_desc, ts.transfer_status_desc, t.account_from, t.account_to, t.amount " +
                "FROM transfer t " +
                "JOIN transfer_type tt USING(transfer_type_id) " +
                "JOIN transfer_status ts USING(transfer_status_id) " +
                "WHERE t.transfer_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        while (results.next()) {
            transfers = mapRowToTransfer(results);
        }
        return transfers;

    }

    @Override
    public List<Transfer> getTransfersByPendingStatus(int currentUserId) {
        List<Transfer> pendingTransfers = new ArrayList<>();
        String sql = "SELECT t.transfer_id, tt.transfer_type_desc, ts.transfer_status_desc, t.account_from, t.account_to, t.amount " +
                "FROM transfer t " +
                "JOIN transfer_type tt USING(transfer_type_id) " +
                "JOIN transfer_status ts USING(transfer_status_id) " +
                "WHERE t.account_from = ? OR t.account_to = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, currentUserId);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            pendingTransfers.add(transfer);
        }

        return pendingTransfers;
    }

    @Override
    public TransferRequestDto createTransferRequest(TransferRequestDto transfer) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?,?,?,?,?) RETURNING transfer_id;";
        Integer transferId = null;
        try {
            transferId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getTransferType(), transfer.getTransferStatus(),
                    transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("User not found to create transfer request.");
        }
        if (transferId == null) {
            return null;
        }
        transfer.setId(transferId);
        return transfer;
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
