package com.keyob.payment.gateway.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.staticRepository.PutExtraKey;

import org.json.JSONObject;

public class WalletDetail extends AppCompatActivity{

    private ImageButton edit_btn;
    private ImageButton delete_btn;
    private JSONObject jsonObject;
    private Long walletId;
    private String publicId;
    private Long walletType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detail);

        edit_btn = (ImageButton) findViewById(R.id.wallet_edit_icon);
        delete_btn = (ImageButton) findViewById(R.id.wallet_delete_icon);

        walletId = getIntent().getLongExtra(PutExtraKey.WALLET_ID, 0L);
        publicId = getIntent().getStringExtra(PutExtraKey.PUBLIC_ID);
        walletType = getIntent().getLongExtra(PutExtraKey.WALLET_TYPE, 0L);

        Intent intent = getIntent();
//        int position = (int) intent.getExtras().get("id");
//        ImageViewAdapter imageAdapter = new ImageViewAdapter(this);
//        ImageView imageView = (ImageView) findViewById(R.id.);
//        imageView.setImageResource(imageAdapter.mThumbIds[position]);
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WalletDetail.this, CreateWalletActivity.class);
                intent.putExtra(PutExtraKey.OWNER,PutExtraKey.EDIT);
                intent.putExtra(PutExtraKey.WALLET_ID ,walletId);
                intent.putExtra(PutExtraKey.PUBLIC_ID ,publicId);
                intent.putExtra(PutExtraKey.WALLET_TYPE ,walletType);
                startActivity(intent);
                finish();
            }
        });
    }

}
