package com.keyob.payment.gateway.network.dto;

public class OauthToken {

    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private long expiredAfterMilli = 0;
    private String refreshToken;


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public long getExpiredAfterMilli() {
        return expiredAfterMilli;
    }

    public void setExpiredAfterMilli(long expiredAfterMilli) {
        this.expiredAfterMilli = expiredAfterMilli;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
