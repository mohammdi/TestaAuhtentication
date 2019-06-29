package com.keyob.payment.gateway.model;

import java.io.Serializable;
import java.util.UUID;

public class RequestMoneyDto implements Serializable {

    private UUID Id;

    private String PayerId;

    private Long WalletId;

    private String PayerName;

    private String SenderId;

    private String SenderName;

    private String RequestToken;

    private String BaseLink;

    private Integer Amount;

    private Boolean Status;

    private String CreateDate;

    private Boolean IsDeleted;

    private Boolean IsDeletedByPayer;

    private Long PayerWalletId;

    private String Description;


    public RequestMoneyDto() {

    }

    public RequestMoneyDto(UUID id, String payerId, Long walletId, String payerName,
                           String senderId, String senderName, String requestToken,
                           String baseLink, Integer amount, Boolean status,
                           String createDate, Boolean isDeleted,
                           Boolean isDeletedByPayer, Long payerWalletId,
                           String description) {
        Id = id;
        PayerId = payerId;
        WalletId = walletId;
        PayerName = payerName;
        SenderId = senderId;
        SenderName = senderName;
        RequestToken = requestToken;
        BaseLink = baseLink;
        Amount = amount;
        Status = status;
        CreateDate = createDate;
        IsDeleted = isDeleted;
        IsDeletedByPayer = isDeletedByPayer;
        PayerWalletId = payerWalletId;
        Description = description;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

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

    public String getRequestToken() {
        return RequestToken;
    }

    public void setRequestToken(String requestToken) {
        RequestToken = requestToken;
    }

    public String getBaseLink() {
        return BaseLink;
    }

    public void setBaseLink(String baseLink) {
        BaseLink = baseLink;
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

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Boolean getDeleted() {
        return IsDeleted;
    }

    public void setDeleted(Boolean deleted) {
        IsDeleted = deleted;
    }

    public Boolean getDeletedByPayer() {
        return IsDeletedByPayer;
    }

    public void setDeletedByPayer(Boolean deletedByPayer) {
        IsDeletedByPayer = deletedByPayer;
    }

    public Long getPayerWalletId() {
        return PayerWalletId;
    }

    public void setPayerWalletId(Long payerWalletId) {
        PayerWalletId = payerWalletId;
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
