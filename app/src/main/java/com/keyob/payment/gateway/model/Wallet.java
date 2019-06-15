package com.keyob.payment.gateway.model;


import java.io.Serializable;

public class Wallet implements Serializable {


    private Long Id;


    private String Name;


    private String CreateDate;

    private String PublicId;

    private String PassPayment;

    private String  Type;

    private Long UserId;

    private Boolean IsDefault;

    private String Address;


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getPublicId() {
        return PublicId;
    }

    public void setPublicId(String publicId) {
        PublicId = publicId;
    }

    public String getPassPayment() {
        return PassPayment;
    }

    public void setPassPayment(String passPayment) {
        PassPayment = passPayment;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
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
//
//    public Boolean getSelected() {
//        return IsSelected;
//    }
//
//    public void setSelected(Boolean selected) {
//        IsSelected = selected;
//    }
//
//    public byte[] getLogo() {
//        return Logo;
//    }
//
//    public void setLogo(byte[] logo) {
//        Logo = logo;
//    }

//    public byte[] getBanner() {
//        return Banner;
//    }
//
//    public void setBanner(byte[] banner) {
//        Banner = banner;
//    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
