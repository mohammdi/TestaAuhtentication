package com.keyob.payment.gateway.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class TagDto implements Serializable {

    // for request
    private String Subject;
    private String Description;
    private int Price;
    private boolean PriceByPayer;
    private int Count;
    private Boolean HasInfinitCount;
    private Long WalletId;

    // for response
    private UUID Id;
    private Boolean IsActive;
    private int PaymentsCount;
    private int TotalPayments;
    private boolean IsDeleted;
    private String BaseLink;
    private String TagToken;
    private Date CreateDate;

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public boolean isPriceByPayer() {
        return PriceByPayer;
    }

    public void setPriceByPayer(boolean priceByPayer) {
        PriceByPayer = priceByPayer;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        this.Count = count;
    }

    public Boolean getHasInfinitCount() {
        return HasInfinitCount;
    }

    public void setHasInfinitCount(Boolean hasInfinitCount) {
        HasInfinitCount = hasInfinitCount;
    }

    public Long getWalletId() {
        return WalletId;
    }

    public void setWalletId(Long walletId) {
        WalletId = walletId;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
    }

    public int getPaymentsCount() {
        return PaymentsCount;
    }

    public void setPaymentsCount(int paymentsCount) {
        PaymentsCount = paymentsCount;
    }

    public int getTotalPayments() {
        return TotalPayments;
    }

    public void setTotalPayments(int totalPayments) {
        TotalPayments = totalPayments;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public String getBaseLink() {
        return BaseLink;
    }

    public void setBaseLink(String baseLink) {
        BaseLink = baseLink;
    }

    public String getTagToken() {
        return TagToken;
    }

    public void setTagToken(String tagToken) {
        TagToken = tagToken;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }
}
