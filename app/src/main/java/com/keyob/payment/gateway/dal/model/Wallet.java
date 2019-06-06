package com.keyob.payment.gateway.dal.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.drawable.Drawable;

import java.io.Serializable;


@Entity(tableName = "wallet")
public class Wallet implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "NAME")
    private String name;

    @ColumnInfo(name = "CREATE_DATE")
    private String createDate;

    @ColumnInfo(name = "PUBLIC_ID")
    private String publicId;

    @ColumnInfo(name = "PASS_PAYMENT")
    private String passPayment;

    @ColumnInfo(name = "WALLET_ADDRESS")
    private String walletAddress;

//    @ColumnInfo(name = "WALLET_TYPE_ID")
    private WalletType walletType;

    @ColumnInfo(name = "USER_ID")
    private Long userId;

    @ColumnInfo(name = "IS_DEFAULT")
    private Boolean isDefault;

    @ColumnInfo(name = "IS_SELECTED")
    private Boolean isSelected;
    /* business wallet type field*/

//    @ColumnInfo(name = "LOGO")
@Ignore
private Drawable logoPath;

//    @ColumnInfo(name = "BANNER_PATH")
    @Ignore
    private  Drawable bannerPath;

    @ColumnInfo(name = "ADDRESS")
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

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
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

    public WalletType getWalletType() {
        return walletType;
    }

    public void setWalletType(WalletType walletType) {
        this.walletType = walletType;
    }
}
