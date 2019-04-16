package com.example.mahmood_mohammadi.testaauhtentication.helper;

import com.example.mahmood_mohammadi.testaauhtentication.dal.l.model.Wallet;
import com.example.mahmood_mohammadi.testaauhtentication.dal.l.model.WalletType;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class ResponseParser {

    public Wallet getWallet(JSONObject jsonObject) throws JSONException {
        Gson gson = new Gson();
        Wallet wallet = gson.fromJson(jsonObject.toString(), Wallet.class);

        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
            String str = it.next();
            if (str.equals("id")) {
                wallet.setId(jsonObject.getLong("id"));
            }
            if (str.equals("name")) {
                wallet.setName(jsonObject.getString("name"));
            }
            if (str.equals("createDate")) {
                wallet.setCreateDate(jsonObject.getString("createDate"));
            }
            if (str.equals("publicId")) {
                wallet.setPublicId(jsonObject.getString("publicId"));
            }
            if (str.equals("passPayment")) {
                wallet.setPassPayment(jsonObject.getString("passPayment"));
            }
            if (str.equals("walletAddress")) {
                wallet.setWalletAddress(jsonObject.getString("walletAddress"));
            }
            if (str.equals("walletType")) {
                JSONObject awalletType = jsonObject.getJSONObject("walletType");
                WalletType walletType = new WalletType();
                walletType.setId(awalletType.getLong("id"));
                walletType.setName(awalletType.getString("name"));
                wallet.setWalletType(walletType);
            }
            if (str.equals("userId")) {
                wallet.setUserId(jsonObject.getLong("userId"));
            }
            if (str.equals("default")) {
                wallet.setDefault(jsonObject.getBoolean("default"));
            }
            if (str.equals("isSelected")) {
                wallet.setSelected(jsonObject.getBoolean("isSelected"));
            }
            if (str.equals("logoPath")) {
                wallet.setLogoPath(jsonObject.getString("logoPath"));
            }
            if (str.equals("bannerPath")) {
                wallet.setBannerPath(jsonObject.getString("bannerPath"));
            }
            if (str.equals("address")) {
                wallet.setAddress(jsonObject.getString("address"));
            }
        }
        return wallet;
    }


}
