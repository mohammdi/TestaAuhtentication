package com.example.mahmood_mohammadi.testaauhtentication.helper;

import android.content.Context;

import com.example.mahmood_mohammadi.testaauhtentication.ObjectModel.Users;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;


public class CurrentUser {

    private Context context;
    private DataSharedPrefrence dshp;

    public CurrentUser(Context context) {
        this.context = context;
    }

    public Users getCurrentUser() throws JSONException {
        HashMap<String, String> dataString = dshp.loadData();
        Users user = new Users();
        user.setId(dataString.get("id"));
        user.setUserId(dataString.get("userId"));
        user.setMobileNumber(dataString.get("mobileNumber"));
        user.setEmail(dataString.get("email"));
        user.setPassword(dataString.get("Password"));

        return user ;

    }

    public void setCurrentUser(Users users) throws JSONException {
        HashMap< String ,String > userData = new HashMap<>();
        userData.put("email" ,users.getEmail());
        userData.put("mobileNumber",users.getMobileNumber());
        userData.put("Password", users.getPassword());
        dshp.savetosharedpref(userData);

    }
}
