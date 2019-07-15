package com.keyob.payment.gateway.model;

import java.util.List;

public class SubmitContactDto {

    private String OwnerMobileNumber;
    private List<ContactDto> contacts;

    public String getOwnerMobileNumber() {
        return OwnerMobileNumber;
    }

    public void setOwnerMobileNumber(String ownerMobileNumber) {
        OwnerMobileNumber = ownerMobileNumber;
    }

    public List<ContactDto> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactDto> contacts) {
        this.contacts = contacts;
    }
}
