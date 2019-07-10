package com.keyob.payment.gateway.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.network.ApiClient;
import com.keyob.payment.gateway.network.RetrofitApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.CONFIRM_CODE;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.PHONE_NUMBER;

public class ConfirmCodeActivity extends AppCompatActivity {

    private Button submitbtn;
    private TextView confCode;
    private TextView resend;
    private RetrofitApiService apiService;
    private String phoneNumber;
    private String confirmCode;
    private LinearLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_code);

        apiService = ApiClient.getInstance().create(RetrofitApiService.class);


        confCode = findViewById(R.id.register_confCode);
        submitbtn = findViewById(R.id.register_btn_submit);
        resend = findViewById(R.id.register_resend);
        rootView = findViewById(R.id.conf_code_rootView);

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra(PHONE_NUMBER);
        confirmCode = intent.getStringExtra(CONFIRM_CODE);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = confCode.getText().toString();
//                if (code.equals(confirmCode)){
                    Intent intent = new Intent(ConfirmCodeActivity.this, RegisterUserDetailsActivity.class);
                    intent.putExtra(PHONE_NUMBER,phoneNumber);
                    startActivity(intent);
//                }
//                else {
//                    Snackbar.make(rootView,"کد وارد شده صحیح نمیباشد!!!",Snackbar.LENGTH_LONG).show();
//                }
            }
        });


        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apiService.registerPhoneNumber(phoneNumber).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                         if (response.isSuccessful()){
                             confirmCode = response.body();
                         }else {
                             Snackbar.make(rootView,"خطا در شبکه",Snackbar.LENGTH_LONG).show();
                         }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Snackbar.make(rootView,"خطا در شبکه",Snackbar.LENGTH_LONG).show();
                    }
                });

            }
        });
    }


}
