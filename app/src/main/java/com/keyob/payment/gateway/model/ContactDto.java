package com.keyob.payment.gateway.model;

import java.util.Date;
import java.util.UUID;


public class ContactDto {

    private UUID Id;
    private String Name;
    private Date CreateDate;
    private String MobileNumber;
    private boolean IsRegistered;
    private String Image;

    public ContactDto() {
    }

    public ContactDto(String name, String mobileNumber) {
        Name = name;
        MobileNumber = mobileNumber;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public boolean isRegistered() {
        return IsRegistered;
    }

    public void setRegistered(boolean registered) {
        IsRegistered = registered;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
