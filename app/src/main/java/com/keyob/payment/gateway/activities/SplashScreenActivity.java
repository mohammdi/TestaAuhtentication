package com.keyob.payment.gateway.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.network.MyURLRepository;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.MESSAGE;

public class SplashScreenActivity extends AppCompatActivity {

    private final static int splash_count =2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Boolean hasNet = statusNetWOrk();
                if (hasNet){

                    Intent intent = new Intent(SplashScreenActivity.this ,LoginActivity.class);
                    startActivity(intent);
                    finish();

                }else {

                    Intent intent = new Intent(SplashScreenActivity.this ,AlarmActivity.class);
                    intent.putExtra(MESSAGE,"MIS_NET");
                    startActivity(intent);
                    finish();
                }


            }
        },splash_count);
    }



    public Boolean statusNetWOrk() {

        ConnectivityManager connect = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = connect.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            return true;

        } else {

            return false;

        }
    }
}