package com.keyob.payment.gateway.helper.DataBase;

import android.content.Context;

import com.keyob.payment.gateway.dal.model.Wallet;
import com.keyob.payment.gateway.staticRepository.PutExtraKey;

import org.json.JSONException;

import java.util.HashMap;

public class ChangeSelectetWallet {

    DataSharedPrefrence dataSharedPrefrence ;

    public void changDefaultWallet(Context context , Wallet wallet ) throws JSONException {

        dataSharedPrefrence = new DataSharedPrefrence(context);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(PutExtraKey.WALLET_NAME ,wallet.getName());
        hashMap.put(PutExtraKey.WALLET_ID,wallet.getId().toString());
        hashMap.put(PutExtraKey.WALLET_PASS,wallet.getPassPayment());
//        hashMap.put(PutExtraKey.WALLET_TYPE,String.valueOf(wallet.getWalletType().getId()));
        hashMap.put(PutExtraKey.PUBLIC_ID,wallet.getPublicId());
        hashMap.put(PutExtraKey.WALLET_ADDRESS,wallet.getAddress());
//        hashMap.put(PutExtraKey.WALLET_LOGO ,wallet.getBannerPath());
//        hashMap.put(PutExtraKey.BANNER,wallet.getLogoPath());

        dataSharedPrefrence.savetosharedpref(hashMap);

    }

}
