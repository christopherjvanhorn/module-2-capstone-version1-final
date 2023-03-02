package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import com.techelevator.tenmo.dao.UserDao;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transfer")
@PreAuthorize("isAuthenticated()")
public class AccountController {
    // TODO Add Dao & Dto
    TransferDao transferDao;
    AccountDao accountDao;
    UserDao userDao;


    public AccountController(TransferDao transferDao, AccountDao accountDao, UserDao userDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @GetMapping(path = "/{userId}")
    @PreAuthorize("permitAll")
    public Account getAccountByUserId(@PathVariable int userId) {
        Account account = accountDao.getAccountByUserId(userId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Account ID.", null);
        } else {
            return account;
        }
    }

//    @GetMapping("balance")
//    public BigDecimal viewCurrentBalance(){
//        return null;
//    }
//
//    @GetMapping()
//    public String viewTransferHistory(){
//        return null;
//    }
//
    @GetMapping("/pending/{userId}")
    public List<TransferPendingDto> viewPendingRequests(@PathVariable Integer userId) {
        List<TransferPendingDto> pendingTransfers = null;
        pendingTransfers = transferDao.getTransfersByPendingStatus(userId);
        if (pendingTransfers != null) {
            return pendingTransfers;
        } else {
            throw new NullPointerException("No pending transfers for the current user");
        }
    }

    @PutMapping("{transferId}")
    public boolean pendingTransferApproval (@PathVariable Integer transferId, boolean approval) {
        Transfer newTransfer = transferDao.getPendingTransfersByTransferId(transferId);
        //if approved: set trans.status to approved, update both account balance
        if (approval){
            sendBucks(accountDao.getAccountBy newTransfer.getAccountFrom(), newTransfer.)
        }
        //if denied: set trans.status to rejected



        return false;
    }

    @PostMapping("send/{senderUserId}/{userIdToSendTo}/{amount}")
    public boolean sendBucks(@PathVariable Integer senderUserId,
                             @PathVariable Integer userIdToSendTo,
                             @PathVariable BigDecimal amount) {
        Account sender = accountDao.getAccountByUserId(senderUserId);
        Account receiver = accountDao.getAccountByUserId(userIdToSendTo);

        if (sender.getBalance().compareTo(amount) >= 0) {
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
            accountDao.updateAccount(sender);
            accountDao.updateAccount(receiver);
        } else {
            throw new IllegalArgumentException("Not enough available funds to send.");
        }
        return true;
    }

    @PostMapping()
    public Transfer createTransfer(@Valid @RequestBody Transfer transfer) {
        if (transfer.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        return transferDao.createTransferRequest(transfer);
    }


    @GetMapping("/users")
    @PreAuthorize("permitAll")
    public List<User> getUsers(){
        return userDao.findAll();
    }


}
