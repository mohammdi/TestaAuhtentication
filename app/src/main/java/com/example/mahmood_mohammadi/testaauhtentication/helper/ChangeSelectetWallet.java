package com.example.mahmood_mohammadi.testaauhtentication.helper;

import android.content.Context;

import com.example.mahmood_mohammadi.testaauhtentication.ObjectModel.Wallet;
import com.example.mahmood_mohammadi.testaauhtentication.staticRepository.PutExtraKey;

import org.json.JSONException;

import java.util.HashMap;

public class ChangeSelectetWallet {

    DataSharedPrefrence dataSharedPrefrence ;

    public void changdefaultWallet(Context context , Wallet wallet ) throws JSONException {

        dataSharedPrefrence = new DataSharedPrefrence(context);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(PutExtraKey.WALLET_NAME ,wallet.getName());
        hashMap.put(PutExtraKey.WALLET_ID,wallet.getId().toString());
        hashMap.put(PutExtraKey.WALLET_PASS,wallet.getPassPayment());
        hashMap.put(PutExtraKey.WALLET_TYPE,wallet.getTypeId().toString());
        hashMap.put(PutExtraKey.PUBLIC_ID,wallet.getPublicId());
        hashMap.put(PutExtraKey.WALLET_ADDRESS,wallet.getAddress());
        hashMap.put(PutExtraKey.WALLET_LOGO ,wallet.getBannerPath().toString());
        hashMap.put(PutExtraKey.BANNER,wallet.getLogoPath().toString());

        dataSharedPrefrence.savetosharedpref(hashMap);

    }

}
