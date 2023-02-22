package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class Account {
    @JsonIgnore
    private int accountId;
    @JsonIgnore
    private int userId;
    private BigDecimal balance;

    //region Constructors, Getters, & Setters
    public Account() {
    }

    public Account(int userId) {
        this.userId = userId;
    }

    public Account(int accountId, int userId) {
        this.accountId = accountId;
        this.userId = userId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    //endregion
}
