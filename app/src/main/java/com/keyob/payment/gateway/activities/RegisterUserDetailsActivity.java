package com.keyob.payment.gateway.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.SingletonUserInfo;
import com.keyob.payment.gateway.model.Users;
import com.keyob.payment.gateway.network.ApiClient;
import com.keyob.payment.gateway.network.RetrofitApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.PHONE_NUMBER;

public class RegisterUserDetailsActivity extends AppCompatActivity {
    private TextView input_email;
    private TextView input_name;
    private TextView input_family;
    private TextView input_pass;
    private TextView input_repeat_pass;
    private Button details_submit;
    private ProgressBar progressBar;
    private LinearLayout rootView;
    private RetrofitApiService apiService;
    private String phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_detaile);

        apiService = ApiClient.getInstance().create(RetrofitApiService.class);

        details_submit = findViewById(R.id.btn_detail_Register);
        input_name = findViewById(R.id.details_userName);
        input_pass = findViewById(R.id.details_password);
        input_repeat_pass = findViewById(R.id.details_password_confirm);
        input_email = findViewById(R.id.details_email);
        input_family = findViewById(R.id.details_family);
        progressBar = findViewById(R.id.user_create_progressbar);
        rootView = findViewById(R.id.register_getDetails_rootView);


        phoneNumber = getIntent().getStringExtra(PHONE_NUMBER);

        details_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = input_email.getText().toString();
                String password = input_pass.getText().toString();
                String name = input_name.getText().toString();
                String family = input_family.getText().toString();
                String confirmPass = input_repeat_pass.getText().toString();


                if (TextUtils.isEmpty(password)) {
                    Snackbar.make(rootView,"رمز عبور را وارد نمایید !!!",3000).show();
                    return;
                }

                if (password.length() < 8) {
                    Snackbar.make(rootView,"حداقل تعداد رمز عبور 8 کاراکتر است",3000).show();
                    return;
                }
                if (TextUtils.isEmpty(name)||TextUtils.isEmpty(family)) {
                    Snackbar.make(rootView,"مشخصات خود را وارد کنید",3000).show();

                    return;
                }

                if (!password.equals(confirmPass)) {
                    Snackbar.make(rootView,"رمز عبور نادرست است ",3000).show();

                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                Users user = new Users();
                user.setFirstName(name);
                user.setLastName(family);
                user.setEmail(email);
                user.setPassWord(password);
                user.setMobileNumber(phoneNumber);

                apiService.registerUser(user.getPassWord(),user.getFirstName(),user.getMobileNumber(),user.getLastName(),user.getEmail()).enqueue(new Callback<Users>() {
                    @Override
                    public void onResponse(Call<Users> call, Response<Users> response) {
                        if (response.isSuccessful()){
                            SingletonUserInfo user = SingletonUserInfo.getInstance();
                            user.replace(response.body());
                            Intent intent = new Intent (RegisterUserDetailsActivity.this,SetDefaultWalletInfoActivity.class);
                            progressBar.setVisibility(View.GONE);
                            startActivity(intent);
                            finish();
                        }else{
                            Snackbar.make(rootView,"خطا در شبکه ... دو باره امتحان کنید. ",3000).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<Users> call, Throwable t) {
                        Snackbar.make(rootView,"خطا در شبکه ... دو باره امتحان کنید. ",Snackbar.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                            call.cancel();
                    }
                });

                progressBar.setVisibility(View.GONE);

            }

        });

    }
}
