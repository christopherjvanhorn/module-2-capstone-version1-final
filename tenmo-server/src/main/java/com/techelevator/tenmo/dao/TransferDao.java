package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;
import java.util.Map;

public interface TransferDao {
    List<Transfer> getTransfersByAccountId(int accountId);
    Transfer getTransfersByTransferId(int transferId);
    List<Transfer> getTransfersByPendingStatus();
}
