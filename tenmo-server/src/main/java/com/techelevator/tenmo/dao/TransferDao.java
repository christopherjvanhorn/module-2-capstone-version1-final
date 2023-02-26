package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    List<Transfer> getTransfersByAccountId(int accountId);
    Transfer getTransfersByTransferId(int transferId);
    List<Transfer> getTransfersByPendingStatus(int currentUserId);
}
