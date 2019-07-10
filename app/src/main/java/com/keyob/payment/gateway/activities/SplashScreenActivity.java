package com.keyob.payment.gateway.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.keyob.payment.gateway.R;

public class SplashScreenActivity extends AppCompatActivity {

    private final static int splash_count =2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this ,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },splash_count);
    }
}