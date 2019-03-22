package com.example.mahmood_mohammadi.testaauhtentication.ObjectModel;

public class WalletDTO {
    private String name;
    private  String  publicId ;
    private String walletAddress;
    private int logoPath;
    private String bannerPath;
    private String address;
    private String invetory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public int getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(int logoPath) {
        this.logoPath = logoPath;
    }

    public String getBannerPath() {
        return bannerPath;
    }

    public void setBannerPath(String bannerPath) {
        this.bannerPath = bannerPath;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInvetory() {
        return invetory;
    }

    public void setInvetory(String invetory) {
        this.invetory = invetory;
    }
}
