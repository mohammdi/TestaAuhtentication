package com.keyob.payment.gateway.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class WalletDto implements Serializable {
    @SerializedName("wallets")
   private List<Foo> wallets ;
    class Foo {
        @SerializedName("id")
        private String id;

        @SerializedName("name")
        private String name;

        @SerializedName("createDate")
        private String createDate;

        @SerializedName("publicId")
        private String publicId;

        @SerializedName("passPayment")
        private String passPayment;

        @SerializedName("walletType")
        private String  walletType;

        @SerializedName("userId")
        private String userId;

        @SerializedName("default")
        private Boolean isDefault;
//        private Boolean isSelected;

        /* business wallet type field*/
        @SerializedName("logo")
        private byte[] logo;

        @SerializedName("banner")
        private byte[] banner;

        @SerializedName("walletAddress")
        private String walletAddress;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getPublicId() {
            return publicId;
        }

        public void setPublicId(String publicId) {
            this.publicId = publicId;
        }

        public String getPassPayment() {
            return passPayment;
        }

        public void setPassPayment(String passPayment) {
            this.passPayment = passPayment;
        }

        public String getWalletType() {
            return walletType;
        }

        public void setWalletType(String walletType) {
            this.walletType = walletType;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Boolean getDefault() {
            return isDefault;
        }

        public void setDefault(Boolean aDefault) {
            isDefault = aDefault;
        }

//        public Boolean getSelected() {
//            return isSelected;
//        }
//
//        public void setSelected(Boolean selected) {
//            isSelected = selected;
//        }

        public byte[] getLogo() {
            return logo;
        }

        public void setLogo(byte[] logo) {
            this.logo = logo;
        }

        public byte[] getBanner() {
            return banner;
        }

        public void setBanner(byte[] banner) {
            this.banner = banner;
        }

        public String getWalletAddress() {
            return walletAddress;
        }

        public void setWalletAddress(String walletAddress) {
            this.walletAddress = walletAddress;
        }

        public void setIsSelected(Integer value){
//            if(value==0){
//                setSelected(false);
//            }else if (value==1){
//                setSelected(false);
//            }
        }
        public void setIsDefault(Integer value){
//            if(value==0){
//                setSelected(false);
//            }else if (value==1){
//                setSelected(false);
//            }
        }
    }
}
