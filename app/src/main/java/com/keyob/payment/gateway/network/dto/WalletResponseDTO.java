package com.keyob.payment.gateway.network.dto;

import com.google.gson.annotations.SerializedName;
import com.keyob.payment.gateway.model.*;

import java.util.List;

public class WalletResponseDTO {

    @SerializedName("wallets")
     List<Wallet> wallets;

    public List<Wallet> getWallets() {
        return wallets;
    }

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
    }
}
