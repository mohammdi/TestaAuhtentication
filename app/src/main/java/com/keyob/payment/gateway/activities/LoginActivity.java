package com.keyob.payment.gateway.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.SingletonUserInfo;
import com.keyob.payment.gateway.helper.dataBase.DataSharedPrefrence;
import com.keyob.payment.gateway.model.Wallet;
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
    private ProgressBar progressBar;

    private static String CLIENT_ID = "7WM5HpvoLf0W.apps.keyob.com";
    private static String CLIENT_SECRET = "hXEKlX5hBm0IZ1eMB9V8tTGp2H6o9cqq";


    private Boolean online = true;
    private String extra_message_internet_statuse;
    private CoordinatorLayout rootView;
    private OauthToken oauthToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logine);
        final String oauthValue = createOauthValue(CLIENT_ID, CLIENT_SECRET);
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        rootView = findViewById(R.id.login_coordinator);
        progressBar = findViewById(R.id.login_progressBar);
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_register = findViewById(R.id.btnLinkToLoginScreen);

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!online) {

                    String internetIsOff = getResources().getString(R.string.internet_is_off_persian);
                    Toast.makeText(context, internetIsOff, Toast.LENGTH_LONG).show();
                    return;

                } else {

                    if (TextUtils.isEmpty(userName.getText().toString())) {
                        Snackbar snackbar = Snackbar.make(rootView, " لطفا نام کاربری خود را وارد کنید!!!", Snackbar.LENGTH_LONG);
                        View view = snackbar.getView();
                        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
                        params.gravity = Gravity.TOP;
                        view.setLayoutParams(params);
                        snackbar.show();
                        return;
                    }
                    if (TextUtils.isEmpty(userName.getText().toString())) {
                        Snackbar snackbar = Snackbar.make(rootView, " لطفا رمز عبور خود را وارد کنید!!!", Snackbar.LENGTH_LONG);
                        View view = snackbar.getView();
                        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
                        params.gravity = Gravity.TOP;
                        view.setLayoutParams(params);
                        snackbar.show();
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);
                    tokenApiService = TokenApiClient.geOauthTokenInstance(oauthValue).create(RetrofitApiService.class);
                    tokenApiService.getAuthorizationToken("password", "openid profile offline_access",
                            userName.getText().toString(), password.getText().toString())
                            .enqueue(new Callback<OauthToken>() {
                                @Override
                                public void onResponse(Call<OauthToken> call, Response<OauthToken> response) {
                                    if (response.isSuccessful()) {
                                        oauthToken = new OauthToken();
                                        oauthToken.setEntity(response.body());
                                        userInfoRequest(oauthToken);
                                    } else {
                                        Snackbar snackbar = Snackbar.make(rootView, "نام کاربری یا پسورد اشبتاه است !!!", Snackbar.LENGTH_LONG);
                                        View view = snackbar.getView();
                                        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
                                        params.gravity = Gravity.TOP;
                                        view.setLayoutParams(params);
                                        snackbar.show();
                                        progressBar.setVisibility(View.GONE);
                                    }

                                }

                                @Override
                                public void onFailure(Call<OauthToken> call, Throwable t) {
                                    Snackbar snackbar = Snackbar.make(rootView, "خطا در شبکه !!!", Snackbar.LENGTH_LONG);
                                    View view = snackbar.getView();
                                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
                                    params.gravity = Gravity.TOP;
                                    view.setLayoutParams(params);
                                    snackbar.show();
                                    progressBar.setVisibility(View.GONE);
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

    private String createOauthValue(String clientId, String clientSecret) {

        StringBuilder sb1 = new StringBuilder();
        sb1.append(clientId);
        sb1.append(":");
        sb1.append(clientSecret);
        String sum = sb1.toString();
        byte[] encrypt = sum.getBytes();
        String base64 = Base64.encodeToString(encrypt, Base64.NO_WRAP);

        StringBuilder sb = new StringBuilder();
        sb.append("Basic");
        sb.append(" ");
        sb.append(base64);
        return sb.toString();

    }

    private void userInfoRequest(OauthToken token) {
        String authorizationToken = "Bearer" + " " + token.getAccess_token();
        userApiService = OauthUserApiClient.geUserInfoInstance(authorizationToken).create(RetrofitApiService.class);
        userApiService.getUserInfo()
                .enqueue(new Callback<OauthUserInfo>() {
                    @Override
                    public void onResponse(Call<OauthUserInfo> call, Response<OauthUserInfo> response) {
                        if (response.isSuccessful()) {
                            SingletonUserInfo user = SingletonUserInfo.getInstance();
                            String id = response.body().getSub();
                            user.setId(Integer.valueOf(id));
                            user.setLastName(response.body().getFamily_name());
                            user.setUserName(response.body().getPreferred_username());
                            user.setMobileNumber(response.body().getPreferred_username());
                            user.setFirstName(response.body().getGiven_name());
                            user.setRefreshToken(oauthToken.getRefresh_token());
                            SingletonUserInfo.getInstance().selfReplace(user);
                            loginToHome();
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Log.i("Login", response.message());
                            Snackbar snackbar = Snackbar.make(rootView, "خطا در شبکه !!!", Snackbar.LENGTH_LONG);
                            View view = snackbar.getView();
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                            params.gravity = Gravity.TOP;
                            view.setLayoutParams(params);
                            snackbar.show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<OauthUserInfo> call, Throwable t) {
                        Snackbar snackbar = Snackbar.make(rootView, "خطا در شبکه !!!", Snackbar.LENGTH_LONG);
                        View view = snackbar.getView();
                        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
                        params.gravity = Gravity.TOP;
                        view.setLayoutParams(params);
                        snackbar.show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void loginToHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}
