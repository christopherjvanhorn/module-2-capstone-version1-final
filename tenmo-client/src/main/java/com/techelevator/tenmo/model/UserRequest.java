package com.techelevator.tenmo.model;

public class UserRequest {
    private int accountNumber;
    private String username;

    public UserRequest(int accountNumber, String username) {
        this.accountNumber = accountNumber;
        this.username = username;
    }

    public UserRequest() {
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
