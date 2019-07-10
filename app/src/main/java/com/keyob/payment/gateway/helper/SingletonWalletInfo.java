package com.keyob.payment.gateway.helper;

import com.keyob.payment.gateway.model.HomeDto;

import java.io.Serializable;

public class SingletonWalletInfo implements Serializable {
    private static SingletonWalletInfo instance;


    private Long id;
    private String WalletName;
    private String walletToken;
    private String baseLink;
    private Integer balance;
    private String publicId;
    private String passPayment;
    private int Type;
    private Long UserId;
    private Boolean IsDefault;
    private String Address;
    private Boolean Selected;
    private String CreateDate;


    public static synchronized SingletonWalletInfo getInstance() {

        if (instance == null) {
            instance = new SingletonWalletInfo();
        }
        return instance;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWalletName() {
        return WalletName;
    }

    public void setWalletName(String walletName) {
        WalletName = walletName;
    }

    public String getWalletToken() {
        return walletToken;
    }

    public void setWalletToken(String walletToken) {
        this.walletToken = walletToken;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getBaseLink() {
        return baseLink;
    }

    public void setBaseLink(String baseLink) {
        this.baseLink = baseLink;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getPassPayment() {
        return passPayment;
    }

    public void setPassPayment(String passPayment) {
        this.passPayment = passPayment;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public Boolean getDefault() {
        return IsDefault;
    }

    public void setDefault(Boolean aDefault) {
        IsDefault = aDefault;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Boolean getSelected() {
        return Selected;
    }

    public void setSelected(Boolean selected) {
        Selected = selected;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public void replace(HomeDto wallet) {
        setId(wallet.getId());
        setPassPayment(wallet.getPassPayment());
        setPublicId(wallet.getPublicId());
        setWalletToken(wallet.getWalletToken());
        setWalletName(wallet.getName());
        setBalance(wallet.getBalance());
        setBaseLink(wallet.getBaseLink());
        setAddress(wallet.getAddress());
        setDefault(wallet.getDefault());
        setCreateDate(wallet.getCreateDate());
        setSelected(wallet.getSelected());
        setType(wallet.getType());
        setUserId(wallet.getUserId());
        setCreateDate(wallet.getCreateDate());
    }

    public HomeDto getWallet() {
        HomeDto w = new HomeDto();
        w.setId(getId());
        w.setPassPayment(getPassPayment());
        w.setPublicId(getPublicId());
        w.setWalletToken(getWalletToken());
        w.setName(getWalletName());
        w.setBalance(getBalance());
        w.setBaseLink(getBaseLink());
        w.setAddress(getAddress());
        w.setUserId(getUserId());
        w.setType(getType());
        w.setDefault(getDefault());
        w.setSelected(getSelected());
        w.setCreateDate(getCreateDate());
        return w;
    }
}
