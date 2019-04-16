package com.example.mahmood_mohammadi.testaauhtentication.activities;

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

import com.android.volley.VolleyError;
import com.example.mahmood_mohammadi.testaauhtentication.dal.l.dao.UserDao;
import com.example.mahmood_mohammadi.testaauhtentication.dal.l.dao.WalletDao;
import com.example.mahmood_mohammadi.testaauhtentication.dal.l.model.Users;
import com.example.mahmood_mohammadi.testaauhtentication.dal.l.model.Wallet;
import com.example.mahmood_mohammadi.testaauhtentication.R;
import com.example.mahmood_mohammadi.testaauhtentication.helper.ApiService;
import com.example.mahmood_mohammadi.testaauhtentication.helper.DataSharedPrefrence;
import com.example.mahmood_mohammadi.testaauhtentication.helper.ResponseParser;
import com.example.mahmood_mohammadi.testaauhtentication.helper.SQLiteHandler;
import com.example.mahmood_mohammadi.testaauhtentication.staticRepository.PutExtraKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private ApiService apiService;
    private Context context = this;
    private EditText userName;
    private EditText password;
    private JSONObject jsonObject;
    private Users myuser = new Users();
    private List<Wallet> walletList = new ArrayList<>();
    private Boolean online = true;
    private String extra_message_internet_statuse;
    private String id;
    private final WalletDao walletDao = new WalletDao(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logine);

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        Button btn_register = (Button) findViewById(R.id.btnLinkToLoginScreen);

        apiService = new ApiService(this);
//        Long userId = addUserToDataBase(context);
//        Users getuser = getuser();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!online) {
                    String internetIsOff = getResources().getString(R.string.internet_is_off_persian);
                    Toast.makeText(context, internetIsOff, Toast.LENGTH_LONG).show();
                } else {
                    // TODO ...TEMPORARY
//                    authenticateRequest();
                    walletListRequset(1L);
                }

                // TODO ...TEMPORARY
//                if(myuser.getId() != null){

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                if (online && extra_message_internet_statuse != null) {
                    intent.putExtra(PutExtraKey.Message, extra_message_internet_statuse);
                } else {
                    intent.putExtra(PutExtraKey.USERS, myuser);
                }
                startActivity(intent);
            }
//            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ConfirmCodeActivity.class);
                startActivity(intent);
            }
        });
    }



    private Users getuser() {
        Users user = new Users();
        UserDao userDao = new UserDao(this);
        HashMap<String, String> userDetails = userDao.getUser();
        user.setId(Long.valueOf(userDetails.get("id")));
        user.setMobileNumber(userDetails.get("mobileNumber"));
        user.setFamily(userDetails.get("createDate"));
        user.setEmail(userDetails.get("email"));
        user.setFamily(userDetails.get("family"));
        user.setName(userDetails.get("name"));
        user.setPassword(userDetails.get("password"));
        return user;
    }


    public void authenticateRequest() {

        Map<String, Users> params = new HashMap<>();
        final Users user = new Users();
        user.setUserName(userName.getText().toString());
        user.setPassword(password.getText().toString());
        params.put("users", user);
        jsonObject = new JSONObject(params);


        /**
         * check internet status
         **/
        online = statusNetWOrk();
        if (online) {

            try {
                apiService.Authenticate(new ApiService.OnResponseReceivedByJsonObject() {
                    @Override
                    public void receive(JSONObject message) {
                        try {
                            SQLiteHandler sqLiteHandler = new SQLiteHandler(context);
                            String typeId = (String) message.get("typeId");
                            sqLiteHandler.addUser(user);
//                            walletListRequset(myuser.getId());

                            // fake data


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(VolleyError message) {
                        Toast.makeText(context, String.valueOf(message), Toast.LENGTH_LONG);
                    }
                }, jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            Toast.makeText(context, "connection unavable", Toast.LENGTH_LONG).show();
        }

    }

    private void walletListRequset(final Long userId) {
        apiService.getWallets(new ApiService.OnResponseReceivedByJsonObject() {
            @Override
            public void receive(JSONObject message) {
                SQLiteHandler sqLiteHandler = new SQLiteHandler(context);
                try {
                    JSONArray jsonArray = message.getJSONArray("wallets");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ResponseParser responseParser = new ResponseParser();
                        Wallet wallet = responseParser.getWallet(jsonObject);
                        walletDao.addWallet(wallet, userId);

                        /**
                         * choose default wallet to select wallet
                         */
                        if (wallet.getDefault()!=null) {
                            if (wallet.getDefault()){
                                wallet.setSelected(true);
                                saveToSharedPreference(wallet);
                            }

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(VolleyError message) {
                Toast.makeText(context, String.valueOf(message), Toast.LENGTH_LONG);

            }
        });
    }

    private void saveToSharedPreference(Wallet wallet) {
        DataSharedPrefrence sharedPreference = new DataSharedPrefrence(context);
        HashMap<String, String> hashMap = DataSharedPrefrence.convertToHashmap(wallet);
        try {
            sharedPreference.savetosharedpref(hashMap);
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
