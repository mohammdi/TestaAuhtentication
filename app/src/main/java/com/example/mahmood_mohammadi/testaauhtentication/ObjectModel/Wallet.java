package com.example.mahmood_mohammadi.testaauhtentication.ObjectModel;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Wallet implements Serializable {

    private Long id ;
    private String name;
    private String createDate ;
    private String  publicId ;
    private String passPayment;
    private String walletAddress;
    private Long TypeId;
    private Long userId;
    private Boolean isdefault;
    private Boolean isSelected;
    /* business wallet type field*/

    private Drawable logoPath;
    private Drawable bannerPath;
    private String address;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String  getPublicId() {
        return publicId;
    }

    public void setPublicId(String  publicId) {
        this.publicId = publicId;
    }

    public String getPassPayment() {
        return passPayment;
    }

    public void setPassPayment(String passPayment) {
        this.passPayment = passPayment;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public Long getTypeId() {
        return TypeId;
    }

    public void setTypeId(Long typeId) {
        TypeId = typeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Drawable getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(Drawable logoPath) {
        this.logoPath = logoPath;
    }

    public Drawable getBannerPath() {
        return bannerPath;
    }

    public void setBannerPath(Drawable bannerPath) {
        this.bannerPath = bannerPath;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Boolean isdefault) {
        this.isdefault = isdefault;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
