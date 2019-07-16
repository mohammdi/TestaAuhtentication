package com.keyob.payment.gateway.model;

import java.util.List;

public class SubmitContactDto {

    private String OwnerMobileNumber;
    private List<ContactLessDto> Contacts;

    public String getOwnerMobileNumber() {
        return OwnerMobileNumber;
    }

    public void setOwnerMobileNumber(String ownerMobileNumber) {
        OwnerMobileNumber = ownerMobileNumber;
    }

    public List<ContactLessDto> getContacts() {
        return Contacts;
    }

    public void setContacts(List<ContactLessDto> contacts) {
        Contacts = contacts;
    }
}
