package com.techelevator.tenmo.model;


import java.math.BigDecimal;

public class Transfer {
    private int id;
    private String transferType;
    private String transferStatus;
    private int accountFrom;
    private int accountTo;

    private BigDecimal amount;

    //region Contructors, Getters, and Setters
    public Transfer() {
    }

    public Transfer(String transferType, String transferStatus,
                    int accountFrom, int accountTo, BigDecimal amount) {
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public Transfer(int id, String transferType, String transferStatus,
                    int accountFrom, int accountTo, BigDecimal amount) {
        this.id = id;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", transferType='" + transferType + "\'" +
                ", transferStatus='" + transferStatus + "\'" +
                ", accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                ", amount=" + amount +
                '}';
    }
    //endregion
}

