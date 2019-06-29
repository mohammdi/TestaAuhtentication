package com.keyob.payment.gateway.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class PassBookResponseDto implements Serializable {

    private UUID CorrelationId;
    private Long participant;
    private int Type;
    private int amount;
    private Date TransactionDate;
    private String Status;
    private String StatusSource;

    public UUID getCorrelationId() {
        return CorrelationId;
    }

    public void setCorrelationId(UUID correlationId) {
        CorrelationId = correlationId;
    }

    public Long getParticipant() {
        return participant;
    }

    public void setParticipant(Long participant) {
        this.participant = participant;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
