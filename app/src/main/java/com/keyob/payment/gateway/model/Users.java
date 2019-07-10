package com.keyob.payment.gateway.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mahmood_Mohammadi on 11/6/2018.
 */

public class Users implements Serializable {

    private int Id;

    private String Username;

    private String MobileNumber;

    private String Email;

    private String FirstName;

    private String LastName;

    private String AboutMe;

    private Boolean Active;

    private Date RegisterDate;


    private String PassWord;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAboutMe() {
        return AboutMe;
    }

    public void setAboutMe(String aboutMe) {
        AboutMe = aboutMe;
    }

    public Boolean getActive() {
        return Active;
    }

    public void setActive(Boolean active) {
        Active = active;
    }

    public Date getRegisterDate() {
        return RegisterDate;
    }

    public void setRegisterDate(Date registerDate) {
        RegisterDate = registerDate;
    }


    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }
}
