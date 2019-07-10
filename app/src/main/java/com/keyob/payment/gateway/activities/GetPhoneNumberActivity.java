package com.keyob.payment.gateway.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.model.Users;
import com.keyob.payment.gateway.network.ApiClient;
import com.keyob.payment.gateway.network.RetrofitApiService;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.CONFIRM_CODE;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.PHONE_NUMBER;

public class GetPhoneNumberActivity extends AppCompatActivity {
    private Users user;
    private Button button;
    private TextView textView;
    private String phoneNumber;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_phone_number);
        button = (Button) findViewById(R.id.btnRegister);
        textView = (TextView) findViewById(R.id.register_phoneNumber);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneNumber = textView.getText().toString();
                Intent intent = new Intent(GetPhoneNumberActivity.this,ConfirmCodeActivity.class);
                intent.putExtra(PHONE_NUMBER,phoneNumber);
                startActivity(intent);


//                RetrofitApiService apiService  = ApiClient.getInstance().create(RetrofitApiService.class);
//                apiService.registerPhoneNumber(phoneNumber).enqueue(new Callback<String>() {
//                    @Override
//                    public void onResponse(Call<String> call, Response<String> response) {
//                        if (response.isSuccessful()){
//                            Intent intent = new Intent(GetPhoneNumberActivity.this,ConfirmCodeActivity.class);
//                            intent.putExtra(PHONE_NUMBER,phoneNumber);
//                            intent.putExtra(CONFIRM_CODE,response.body());
//                            startActivity(intent);
//                        }else {
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<String> call, Throwable t) {
//                        call.cancel();
//                    }
//                });

            }
        });

    }



}
