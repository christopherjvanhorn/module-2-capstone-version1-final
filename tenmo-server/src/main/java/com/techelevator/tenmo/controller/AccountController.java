package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.PermitAll;
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
//    @GetMapping()
//    public String viewPendingRequests() {
//        return null;
//    }


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
    public TransferRequestDto createTransfer(@Valid @RequestBody TransferRequestDto transferRequestDto) {
        if (transferRequestDto.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        return transferDao.createTransferRequest(transferRequestDto);
    }


    @GetMapping("/users")
    @PreAuthorize("permitAll")
    public List<User> getUsers(){
        return userDao.findAll();
    }


}
