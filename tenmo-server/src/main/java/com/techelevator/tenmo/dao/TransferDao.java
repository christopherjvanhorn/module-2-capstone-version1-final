package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferPendingDto;
import com.techelevator.tenmo.model.TransferRequestDto;

import java.util.List;

public interface TransferDao {
    List<Transfer> getTransfersByAccountId(int accountId);
    Transfer getTransfersByTransferId(int transferId);
    List<TransferPendingDto> getTransfersByPendingStatus(int currentUserId);

    TransferRequestDto createTransferRequest(TransferRequestDto transfer);
}
