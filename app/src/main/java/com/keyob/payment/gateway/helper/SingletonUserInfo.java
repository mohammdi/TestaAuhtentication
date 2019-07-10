package com.keyob.payment.gateway.helper;

import com.keyob.payment.gateway.model.Users;

import java.io.Serializable;
import java.util.Date;

public class SingletonUserInfo implements Serializable {
    private static SingletonUserInfo instance ;



    private Integer Id;

    private String UserName;

    private String MobileNumber;

    private String Email;

    private String FirstName;

    private String LastName;

    private String AboutMe;

    private Boolean Active;

    private Date RegisterDate;

    private byte[] Image;

    public static synchronized SingletonUserInfo getInstance(){

        if (instance==null){
            instance= new SingletonUserInfo();
        }
            return instance;
    }


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
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

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }


    public  void replace(Users user){
        setId(user.getId());
        setAboutMe(user.getAboutMe());
        setActive(user.getActive());
        setEmail(user.getEmail());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setMobileNumber(user.getMobileNumber());
        setRegisterDate(user.getRegisterDate());
        setUserName(user.getUsername());
    }
}
