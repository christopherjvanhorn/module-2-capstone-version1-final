package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferRequestDto;

import java.util.List;

public interface TransferDao {
    List<Transfer> getTransfersByUserId(int accountId);
    Transfer getTransfersByTransferId(int transferId);
    List<Transfer> getTransfersByPendingStatus(int currentUserId);

    TransferRequestDto createTransferRequest(TransferRequestDto transfer);
}
