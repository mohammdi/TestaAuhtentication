package com.example.mahmood_mohammadi.testaauhtentication.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mahmood_mohammadi.testaauhtentication.R;

public class SplashScreenActivity extends AppCompatActivity {

    private final static int splash_count =4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this ,LogineActivity.class);
                startActivity(intent);
                finish();
            }
        },splash_count);
    }
}