package com.keyob.payment.gateway.helper.dataBase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.model.Wallet;
import com.keyob.payment.gateway.staticRepository.PutExtraKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.BALANCE;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.WALLET_Default;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.WALLET_ID;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.WALLET_NAME;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.WALLET_TAG;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.WALLET_TYPE;

/**
 * Created by Mahmood_mohammadi on 3/27/2018.
 */

public class DataSharedPrefrence {

    public static final String TAG_SAVE_PREFERENCE = "save_preference";
    private SharedPreferences sharedPreferences;
    private Context context;

    public DataSharedPrefrence(Context context){

        sharedPreferences = context.getSharedPreferences("user storage", context.MODE_PRIVATE);
    }

   /**
    * save selected  wallet that to share preference to access all app part
    */
    public void saveToSharedPref(HashMap<String,String> data) throws JSONException {

        if (data != null) {
            SharedPreferences.Editor e = sharedPreferences.edit();
            JSONObject jsonObject = new JSONObject();
            for (String d : data.keySet()) {
                jsonObject.put("name", d);
                jsonObject.put("value", data.get(d));
            }
            e.putString("selected_wallet", jsonObject.toString());
            e.apply();
        }
        else {
            Log.i(TAG_SAVE_PREFERENCE,"nothing data for save !!!! ");
        }
    }


    public HashMap<String,String> loadData() throws JSONException {
        String data = sharedPreferences.getString("saveData", null);
        HashMap<String,String > user= new HashMap<>();
        if (data!=null){
            JSONArray arr = new JSONArray(data);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject json = arr.getJSONObject(i);
                String  value = json.getString("value");
                user.put(json.getString("name"),value);
            }
        }
        return user;
    }


    public static HashMap<String,String> convertToHashmap(Wallet wallet){
        HashMap<String,String> map = new HashMap<>();
        map.put(WALLET_ID,String.valueOf(wallet.getId()));
        map.put(PutExtraKey.WALLET_NAME,wallet.getName());
//        map.put(PutExtraKey.WALLET_TYPE,String.valueOf(wallet.getWalletType()));
//        map.put(PutExtraKey.USER_ID,String.valueOf(wallet.getUserId()));
//        map.put(PutExtraKey.WALLET_CREATE_DATE,String.valueOf(wallet.getCreateDate()));
//        map.put(PutExtraKey.WALLET_ADDRESS,wallet.getAddress());
//        map.put(PutExtraKey.WALLET_PASS,wallet.getPassPayment());
//        map.put(PutExtraKey.WALLET_Default,String.valueOf(wallet.getDefault()));
//        map.put(PutExtraKey.WALLET_SELECTED,String.valueOf(wallet.getSelected()));
        return map;
    }

//    public static HashMap<String,String> homeDtoConvertToHashmap(HomeDto wallet){
//        StringBuilder sb = new StringBuilder();
//        sb.append(wallet.getBaseLink());
//        sb.append(wallet.getWalletToken());
//        HashMap<String,String> map = new HashMap<>();
//        map.put(WALLET_ID,String.valueOf(wallet.getId()));
//        map.put(PutExtraKey.WALLET_NAME,wallet.getName());
//        map.put(PutExtraKey.USER_ID,String.valueOf(wallet.getUserId()));
//        map.put(PutExtraKey.WALLET_CREATE_DATE,String.valueOf(wallet.getCreateDate()));
//        map.put(PutExtraKey.WALLET_ADDRESS,wallet.getAddress());
//        map.put(PutExtraKey.WALLET_PASS,wallet.getPassPayment());
//        map.put(PutExtraKey.WALLET_Default,String.valueOf(wallet.getDefault()));
//        map.put(PutExtraKey.WALLET_TAG,sb.toString());
//        map.put(BALANCE,String.valueOf(wallet.getBalance()));
//        return map;
//    }


//    public static HomeDto hashMapConvertToHomeDto(HashMap hashMap){
//        HomeDto homeDto = new HomeDto();
//        homeDto.setId((Long) hashMap.get(WALLET_ID));
//        homeDto.setType((String) hashMap.get(WALLET_TYPE));
//        homeDto.setBalance((Integer)hashMap.get(BALANCE));
//        homeDto.setBaseLink((String)hashMap.get(WALLET_TAG));
//        homeDto.setName((String)hashMap.get(WALLET_NAME));
//        homeDto.setDefault((Boolean)hashMap.get(WALLET_Default));
//        return homeDto;
//    }

}
