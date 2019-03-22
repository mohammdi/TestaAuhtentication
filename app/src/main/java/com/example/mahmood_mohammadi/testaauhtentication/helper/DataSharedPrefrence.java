package com.example.mahmood_mohammadi.testaauhtentication.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Mahmood_mohammadi on 3/27/2018.
 */

public class DataSharedPrefrence {

    private SharedPreferences sharedPreferences;
    private Context context;

    public DataSharedPrefrence(Context context) {

        sharedPreferences = context.getSharedPreferences("user storage", context.MODE_PRIVATE);
    }


    public void savetosharedpref(HashMap<String,String> data) throws JSONException {

        if (data != null) {
            SharedPreferences.Editor e = sharedPreferences.edit();
            JSONArray jsonArray = new JSONArray();

            for (String d : data.keySet()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", d);
                jsonObject.put("value", data.get(d));
                jsonArray.put(jsonObject);


            }
            e.putString("saveData", jsonArray.toString());
            e.apply();
        }
        else {
            Log.i("rrrrr","nothing data for save !!!! ");
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
}
