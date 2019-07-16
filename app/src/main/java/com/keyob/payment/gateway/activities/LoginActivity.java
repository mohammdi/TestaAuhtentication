package com.keyob.payment.gateway.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.keyob.payment.gateway.helper.SingletonUserInfo;
import com.keyob.payment.gateway.model.Wallet;
import com.keyob.payment.gateway.R;
//import com.example.mahmood_mohammadi.testaauhtentication.helper.DataBase.AppDataBase;
import com.keyob.payment.gateway.helper.dataBase.DataSharedPrefrence;
import com.keyob.payment.gateway.network.ApiClient;
import com.keyob.payment.gateway.network.OauthUserApiClient;
import com.keyob.payment.gateway.network.RetrofitApiService;
import com.keyob.payment.gateway.network.TokenApiClient;
import com.keyob.payment.gateway.network.dto.OauthToken;
import com.keyob.payment.gateway.network.dto.OauthUserInfo;

import org.json.JSONException;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private Context context = this;
    private EditText userName;
    private EditText password;
    private RetrofitApiService tokenApiService;
    private RetrofitApiService userApiService;

    private static String CLIENT_ID = "7WM5HpvoLf0W.apps.keyob.com";
    private static String CLIENT_SECRET = "hXEKlX5hBm0IZ1eMB9V8tTGp2H6o9cqq";


    private Boolean online = true;
    private String extra_message_internet_statuse;
    private RelativeLayout rootView;
    private OauthToken oauthToken;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logine);

        final String oauthValue = createOauthValue(CLIENT_ID, CLIENT_SECRET);
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        rootView = findViewById(R.id.login_root_view);
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_register = findViewById(R.id.btnLinkToLoginScreen);


//        SingletonUserInfo instance = SingletonUserInfo.getInstance();
//        instance.setMobileNumber("09194268369");
//        instance.setId(15);
        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!online) {
                    String internetIsOff = getResources().getString(R.string.internet_is_off_persian);
                    Toast.makeText(context, internetIsOff, Toast.LENGTH_LONG).show();
                    return;
                } else {
                    tokenApiService = TokenApiClient.geOauthTokenInstance(oauthValue).create(RetrofitApiService.class);
                    tokenApiService.getAuthorizationToken("password","openid profile offline_access",
                                                        userName.getText().toString(),password.getText().toString())
                            .enqueue(new Callback<OauthToken>() {
                                @Override
                                public void onResponse(Call<OauthToken> call, Response<OauthToken> response) {
                                    if (response.isSuccessful()){
                                         oauthToken = new OauthToken();
                                         oauthToken.setEntity(response.body());
                                        userInfoRequest(oauthToken);
                                    }else {
                                        Snackbar  snackbar = Snackbar.make(rootView,"نام کاربری یا پسورد اشبتاه است !!!",Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<OauthToken> call, Throwable t) {
                                    Snackbar  snackbar = Snackbar.make(rootView,"خطا در شبکه !!!",Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            });
                }
            }
        });



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, GetPhoneNumberActivity.class);
                startActivity(intent);
            }
        });
    }

    private String  createOauthValue(String clientId ,String clientSecret) {

        StringBuilder sb1 = new StringBuilder();
        sb1.append(clientId);
        sb1.append(":");
        sb1.append(clientSecret);
        String sum =sb1.toString();
        byte[] encrypt= sum.getBytes();
        String base64 = Base64.encodeToString(encrypt, Base64.NO_WRAP);

        StringBuilder sb = new StringBuilder();
        sb.append("Basic");
        sb.append(" ");
        sb.append(base64);
       return sb.toString();

    }


    private void userInfoRequest(OauthToken token){
        String authorizationToken = "Bearer"+" "+token.getAccess_token();
        userApiService = OauthUserApiClient.geUserInfoInstance(authorizationToken).create(RetrofitApiService.class);
        userApiService.getUserInfo().enqueue(new Callback<OauthUserInfo>() {
            @Override
            public void onResponse(Call<OauthUserInfo> call, Response<OauthUserInfo> response) {
                if ( response.isSuccessful()){
                    SingletonUserInfo user = SingletonUserInfo.getInstance();
                    String id = response.body().getSub();
                    user.setId(Integer.valueOf(id));
                    user.setLastName(response.body().getFamily_name());
                    user.setUserName(response.body().getPreferred_username());
                    user.setMobileNumber(response.body().getPreferred_username());
                    user.setFirstName(response.body().getGiven_name());
                    SingletonUserInfo.getInstance().selfReplace(user);
                    loginToHome();
                 }else {
                    Log.i("Login",response.errorBody().toString());
                    Snackbar  snackbar = Snackbar.make(rootView,"خطا در شبکه !!!",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<OauthUserInfo> call, Throwable t) {
                Log.i("Login",t.getCause().toString());
                Snackbar  snackbar = Snackbar.make(rootView,"خطا در شبکه !!!",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    private void loginToHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
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
