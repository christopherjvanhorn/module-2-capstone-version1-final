package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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


    @PutMapping("send/{senderUserId}/{userIdToSendTo}/{amount}")
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
            //TODO create new transfer
        } else {
            throw new IllegalArgumentException("Not enough available funds to send.");
        }
        return true;
    }

    @PutMapping("request/{requestingUserId}/{userIdToRequestFrom}/{amount}")
    public boolean requestBucks(@PathVariable Integer requestingUserId,
                                @PathVariable Integer userIdToRequestFrom,
                                @PathVariable Integer amount) {
        Account user = accountDao.getAccountByUserId(requestingUserId);
        //TODO create new transfer with status PENDING
        return false;
    }

    @GetMapping("/users")
    @PreAuthorize("permitAll")
    public List<User> getUsers(){
        return userDao.findAll();
    }


}
