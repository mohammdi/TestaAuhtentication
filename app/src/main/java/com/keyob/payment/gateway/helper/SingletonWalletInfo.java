package com.keyob.payment.gateway.helper;

import java.io.Serializable;

public class SingletonWalletInfo implements Serializable {
    private static SingletonWalletInfo instance ;



    private Long id;
    private String WalletName;
    private String walletToken;
    private String baseLink;
    private Integer balance;
    private String publicId;
    private String passPayment;

    public static synchronized SingletonWalletInfo getInstance(){

        if (instance==null){
            instance= new SingletonWalletInfo();
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
}
