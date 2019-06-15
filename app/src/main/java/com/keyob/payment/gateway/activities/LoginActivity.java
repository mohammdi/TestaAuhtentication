package com.keyob.payment.gateway.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.keyob.payment.gateway.model.Users;
import com.keyob.payment.gateway.model.Wallet;
import com.keyob.payment.gateway.R;
//import com.example.mahmood_mohammadi.testaauhtentication.helper.DataBase.AppDataBase;
import com.keyob.payment.gateway.helper.dataBase.DataSharedPrefrence;
import com.keyob.payment.gateway.staticRepository.PutExtraKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LoginActivity extends AppCompatActivity {

    private Context context = this;
    private EditText userName;
    private EditText password;
    private JSONObject jsonObject;
    private Users myuser = new Users();
    private List<Wallet> walletList = new ArrayList<>();
    private Boolean online = true;
    private String extra_message_internet_statuse;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logine);

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        Button btn_register = (Button) findViewById(R.id.btnLinkToLoginScreen);

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!online) {
                    String internetIsOff = getResources().getString(R.string.internet_is_off_persian);
                    Toast.makeText(context, internetIsOff, Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                if (online && extra_message_internet_statuse != null) {
                    intent.putExtra(PutExtraKey.Message, extra_message_internet_statuse);
                } else {
                    intent.putExtra(PutExtraKey.USERS, myuser);
                }
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ConfirmCodeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveToSharedPreference(Wallet wallet) {
        DataSharedPrefrence sharedPreference = new DataSharedPrefrence(context);
        HashMap<String, String> hashMap = DataSharedPrefrence.convertToHashmap(wallet);
        try {
            sharedPreference.saveToSharedPref(hashMap);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public Boolean statusNetWOrk() {

        ConnectivityManager connect = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = connect.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            return true;

        } else {

            return false;

        }
    }


    }
