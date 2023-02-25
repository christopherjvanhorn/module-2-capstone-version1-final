package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("")
@PreAuthorize("isAuthenticated()")
public class AccountController {
    //TODO Add Dao & Dto
    TransferDao transferDao;
    AccountDao accountDao;
    UserDao userDao;

    public AccountController(TransferDao transferDao, AccountDao accountDao, UserDao userDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }


    @GetMapping(path = "/{userId}")
    public Account getAccountByUserId(@PathVariable int userId){
        Account account = accountDao.getAccountByUserId(userId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Account ID.", null);
        } else {
            return account;
        }
    }

//    @GetMapping()
//    public String viewTransferHistory(){
//        return null;
//    }
//
//    @GetMapping()
//    public String viewPendingRequests() {
//        return null;
//    }
//
//    @PutMapping()
//    public boolean sendBucks() {
//        return false;
//    }
//
//    @PutMapping()
//    public boolean requestBucks() {
//        return false;
//    }


}
