package com.keyob.payment.gateway.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.helper.URLAttacher;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.model.Wallet;
import com.keyob.payment.gateway.network.MyURLRepository;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.PAYMENT_UUID;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.WALLET;

public class WalletInfoActivity extends AppCompatActivity {

    private TextView name;
    private TextView publicId;
    private ImageView image;
    private EditText pass;
    private EditText amount;
    private Button payBtn;
    private RelativeLayout rootView;
    private WalletViewModelNetWork viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_info);
        name = findViewById(R.id.wallet_info_name);
        publicId = findViewById(R.id.wallet_info_publicId);
        image = findViewById(R.id.wallet_info_image);
        pass = findViewById(R.id.wallet_info_passPayment);
        amount = findViewById(R.id.wallet_info_amount);
        payBtn = findViewById(R.id.wallet_info_pay_btn);
        rootView = findViewById(R.id.wallet_info_root_view);

        Intent intent = getIntent();
        final HomeDto wallet= (HomeDto) intent.getSerializableExtra(WALLET);
        name.setText(wallet.getName());
        publicId.setText(wallet.getPublicId());
        String uri = URLAttacher.doAttach(MyURLRepository.GET_LOGO_BY_WALLET_ID, String.valueOf(wallet.getId()), null);
        Picasso.with(getApplicationContext()).load(uri).into(image);

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = pass.getText().toString();
                if (password!=null && password.equals(SingletonWalletInfo.getInstance().getPassPayment())){
                    if (String.valueOf(amount.getText())!=null){
                        viewModel = ViewModelProviders.of(WalletInfoActivity.this).get(WalletViewModelNetWork.class);
                        viewModel.pTOpPayment(SingletonWalletInfo.getInstance().getId(),wallet.getId(),Integer.valueOf(amount.getText().toString()))
                        .observe(WalletInfoActivity.this, new Observer<String>() {
                            @Override
                            public void onChanged(@Nullable String correlationId) {
                                Intent intent = new Intent(WalletInfoActivity.this,ReportRequestPaymentActivity.class);
                                intent.putExtra(PAYMENT_UUID,correlationId);
                                startActivity(intent);
                            }
                        });
                    }else{
                         Snackbar.make(rootView,"لطفا مبلغ را وارد کنید",Snackbar.LENGTH_LONG).show();
                    }
                }else {
                    Snackbar.make(rootView,"پسورد اشتباه است !!!",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        setUpToolBar();
    }


    public void setUpToolBar(){
        Toolbar toolbar = findViewById(R.id.wallet_info_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setElevation(10);
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.wallet_item_gradient_selector));
    }
}
