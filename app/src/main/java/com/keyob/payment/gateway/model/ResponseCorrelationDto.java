package com.keyob.payment.gateway.model;

import java.util.Date;

public class ResponseCorrelationDto {

    private String SenderId;
    private String ReceiverId;
    private Integer Amount;
    private Date TransactionDate;
    private String Status;
    private String StatusSource;


    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getReceiverId() {
        return ReceiverId;
    }

    public void setReceiverId(String receiverId) {
        ReceiverId = receiverId;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public Date getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        TransactionDate = transactionDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStatusSource() {
        return StatusSource;
    }

    public void setStatusSource(String statusSource) {
        StatusSource = statusSource;
    }
}
