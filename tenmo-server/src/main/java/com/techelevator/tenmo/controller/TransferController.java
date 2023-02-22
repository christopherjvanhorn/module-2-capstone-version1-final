package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transfer")
//@PreAuthorize("isAuthenticated()")
public class TransferController {
    //TODO Add Dao & Dto
    JdbcTransferDao transferDao;
    JdbcAccountDao accountDao;
    JdbcUserDao userDao;

    public TransferController(JdbcTransferDao transferDao, JdbcAccountDao accountDao, JdbcUserDao userDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @GetMapping("balance")
    public BigDecimal viewCurrentBalance(){
        return null;
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
