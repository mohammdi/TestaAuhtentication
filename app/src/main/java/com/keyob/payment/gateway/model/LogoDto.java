package com.keyob.payment.gateway.model;

import java.io.Serializable;

public class LogoDto implements Serializable {

    private byte[] Logo;


    public byte[] getLogo() {
        return Logo;
    }

    public void setLogo(byte[] logo) {
        Logo = logo;
    }
}
