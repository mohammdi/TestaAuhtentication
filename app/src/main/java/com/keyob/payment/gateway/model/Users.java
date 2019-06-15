package com.keyob.payment.gateway.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Mahmood_mohammadi on 11/6/2018.
 */
@Entity(tableName = "user")
public class Users implements Serializable {

    @PrimaryKey
    private Long  id;

    @ColumnInfo(name = "USER_NAME")
    private String userName;

    @ColumnInfo(name="MOBILE_NUMBER")
    private String mobileNumber;

    @ColumnInfo(name = "PASSWORD")
    private String Password;

    @ColumnInfo(name="EMAIL")
    private String email;

    @ColumnInfo(name = "NAME")
    private String name ;

    @ColumnInfo(name = "FAMILY")
    private String family;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }
}
