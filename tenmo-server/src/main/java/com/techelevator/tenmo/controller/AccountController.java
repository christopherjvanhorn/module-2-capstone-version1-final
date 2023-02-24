package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transfer")
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

    @GetMapping("balance")
    public BigDecimal viewCurrentBalance(){
        return null;
    }

    @GetMapping()
    public String viewTransferHistory(){
        return null;
    }

    @GetMapping("pending")
    public List<Transfer> viewPendingRequests(int currentUserId) {
        return transferDao.getTransfersByPendingStatus(currentUserId);
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
