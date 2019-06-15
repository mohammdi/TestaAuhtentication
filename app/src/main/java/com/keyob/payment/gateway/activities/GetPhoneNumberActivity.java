package com.keyob.payment.gateway.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.keyob.payment.gateway.model.Users;
import com.keyob.payment.gateway.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GetPhoneNumberActivity extends AppCompatActivity {
    private Users user;
    private Button button;
    private TextView textView;
    private String email;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_phone_number);
        button = (Button) findViewById(R.id.btnRegister);
        textView = (TextView) findViewById(R.id.Email);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = textView.getText().toString();

            }
        });

    }



}
