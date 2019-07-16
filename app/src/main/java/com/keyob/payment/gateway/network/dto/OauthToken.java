package com.keyob.payment.gateway.network.dto;

public class OauthToken {

    private String access_token;
    private String token_type;
    private long expire_in ;
    private String refresh_token;


    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public long getExpire_in() {
        return expire_in;
    }

    public void setExpire_in(long expire_in) {
        this.expire_in = expire_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }


    public  void setEntity (OauthToken entity) {
        setAccess_token(entity.getAccess_token());
        setExpire_in(entity.getExpire_in());
        setRefresh_token(entity.getRefresh_token());
        setToken_type(entity.getToken_type());
    }


}
