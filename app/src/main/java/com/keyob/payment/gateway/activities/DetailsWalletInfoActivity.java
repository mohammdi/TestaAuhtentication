package com.keyob.payment.gateway.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.keyob.payment.gateway.R;

public class DetailsWalletInfoActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView link;
    private TextView name;
    private TextView publicId;
    private Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_wallet_info);
        imageView =findViewById(R.id.details_wallet_image);
        link =findViewById(R.id.details_wallet_link);
        name =findViewById(R.id.details_wallet_name);
        publicId =findViewById(R.id.details_wallet_publicId);
        confirm =findViewById(R.id.details_wallet_confirm);


    }
}
