package com.keyob.payment.gateway.model;

import com.keyob.payment.gateway.helper.enums.EntryType;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class PassBookResponseDto implements Serializable {

    private UUID CorrelationId;
    private String  Participant;
    private int Type;
    private int Amount;
    private Date TransactionDate;
    private Boolean Status;
    private String StatusSource;

    public UUID getCorrelationId() {
        return CorrelationId;
    }

    public void setCorrelationId(UUID correlationId) {
        CorrelationId = correlationId;
    }

    public String getParticipant() {
        return Participant;
    }

    public void setParticipant(String participant) {
        Participant = participant;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public Date getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        TransactionDate = transactionDate;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public String getStatusSource() {
        return StatusSource;
    }

    public void setStatusSource(String statusSource) {
        StatusSource = statusSource;
    }
}
