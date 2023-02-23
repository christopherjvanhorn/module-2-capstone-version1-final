package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
//@RequestMapping("/transfer")
//@PreAuthorize("isAuthenticated()")
public class AccountController {
    //TODO Add Dao & Dto
    JdbcTransferDao transferDao;
    JdbcAccountDao accountDao;
    JdbcUserDao userDao;

    public AccountController(JdbcTransferDao transferDao, JdbcAccountDao accountDao, JdbcUserDao userDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @GetMapping(path = "/{accountId}")
    public BigDecimal viewCurrentBalance(@PathVariable int accountId){
        Account account = accountDao.getAccountById(accountId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Account ID.", null);
        } else {
            return account.getBalance();
        }
    }

    @GetMapping()
    public String viewTransferHistory(){
        return null;
    }

    @GetMapping()
    public String viewPendingRequests() {
        return null;
    }

    @PutMapping()
    public boolean sendBucks() {
        return false;
    }

    @PutMapping()
    public boolean requestBucks() {
        return false;
    }


}
