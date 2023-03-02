package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferPendingDto;
import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    List<Transfer> getTransfersByAccountId(int accountId);
    Transfer getTransfersByTransferId(int transferId);

    Transfer getPendingTransfersByTransferId(int transferId);

    List<TransferPendingDto> getTransfersByPendingStatus(int currentUserId);

    Transfer createTransferRequest(Transfer transfer);

    Transfer updateTransferRequest(Transfer transfer);
}
