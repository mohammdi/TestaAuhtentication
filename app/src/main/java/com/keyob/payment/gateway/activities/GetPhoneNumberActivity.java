package com.keyob.payment.gateway.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.keyob.payment.gateway.dal.model.Users;
import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.restClient.ApiService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GetPhoneNumberActivity extends AppCompatActivity {
    private Users user;
    private ApiService apiService;
    private Button button;
    private TextView textView;
    private String email;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_phone_number);
        apiService = new ApiService(this);
        button = (Button) findViewById(R.id.btnRegister);
        textView = (TextView) findViewById(R.id.Email);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = textView.getText().toString();
                sendReq(email);

            }
        });

    }

    private void sendReq(String email) {

        Map<String, String> params = new HashMap<String, String>();

        params.put("email", email);
        jsonObject = new JSONObject(params);
        apiService.getConFirmCode(new ApiService.OnResponsReceive() {
            @Override
            public void receive(String message) {
                if (message != null) {

                    Intent intent = new Intent(GetPhoneNumberActivity.this, ConfirmCodeActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    Toast.makeText(GetPhoneNumberActivity.this, "your email incorrect\n please try again", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onError() {
                Toast.makeText(GetPhoneNumberActivity.this, "some error from server!!! \n please try again", Toast.LENGTH_LONG).show();
            }
        }, jsonObject);

    }

}
