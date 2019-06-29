package com.keyob.payment.gateway.model;

import java.io.Serializable;
import java.util.UUID;

public class CreateRequestMoneyDto implements Serializable {

    private String PayerId;

    private Long WalletId;

    private String PayerName;

    private Integer Amount;

    private Long PayerWalletId;

    private String Description;

    private String SenderId;

    private String SenderName;

    public String getPayerId() {
        return PayerId;
    }

    public void setPayerId(String payerId) {
        PayerId = payerId;
    }

    public Long getWalletId() {
        return WalletId;
    }

    public void setWalletId(Long walletId) {
        WalletId = walletId;
    }

    public String getPayerName() {
        return PayerName;
    }

    public void setPayerName(String payerName) {
        PayerName = payerName;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public Long getPayerWalletId() {
        return PayerWalletId;
    }

    public void setPayerWalletId(Long payerWalletId) {
        PayerWalletId = payerWalletId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }
}
