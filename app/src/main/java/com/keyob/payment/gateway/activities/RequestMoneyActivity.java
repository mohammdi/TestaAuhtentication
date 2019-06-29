package com.keyob.payment.gateway.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.model.RequestMoneyDto;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;

import java.util.regex.Pattern;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.AMOUNT;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.DESCRIPTION;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.PAYER_WALLET_ID;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.PUBLIC_ID;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.WALLET_ID;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.WALLET_NAME;

public class RequestMoneyActivity extends AppCompatActivity {

    private TextView cost;
    private TextView description;
    private TextView publicId_phoneNnumber;
    private Button confirm;
    private Long walletId;
    private WalletViewModelNetWork viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_money);

        cost= findViewById(R.id.req_money_cost);
        description = findViewById(R.id.req_money_description);
        publicId_phoneNnumber = findViewById(R.id.req_money_pId_phNumber);
        confirm = findViewById(R.id.req_money_confirm);

        walletId= SingletonWalletInfo.getInstance().getId();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                boolean result = validate();
                RequestMoneyDto requestMoneyDto = new RequestMoneyDto();
                requestMoneyDto.setAmount(Integer.valueOf(cost.getText().toString()));
                requestMoneyDto.setWalletId(walletId);
                requestMoneyDto.setDescription(description.getText().toString());
                requestMoneyDto.setPayerId(publicId_phoneNnumber.getText().toString());
                String payerId= publicId_phoneNnumber.getText().toString();

                String regex ="^(\\+98|0)?9\\d{9}$";
                boolean isPhoneNumber = Pattern.matches(regex,payerId);
                if (isPhoneNumber){
                    getWalletByPhoneNumber(payerId);
                }else {
                    getWalletBuyPublicId(payerId);
                }
            }
        });
    }

    private void getWalletByPhoneNumber(final String payerId) {
        viewModel = ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
        viewModel.findWalletByPhoneNumber(payerId).observe(this, new Observer<HomeDto>() {
            @Override
            public void onChanged(@Nullable HomeDto homeDto) {
                if (homeDto!=null){
                    Intent intent = new Intent(RequestMoneyActivity.this,ReqShowWalletInfoActivity.class);
                    intent.putExtra(WALLET_NAME,homeDto.getName());
                    intent.putExtra(PUBLIC_ID,publicId_phoneNnumber.getText().toString());
                    intent.putExtra(PAYER_WALLET_ID,homeDto.getId());
                    intent.putExtra(AMOUNT,cost.getText().toString());
                    intent.putExtra(DESCRIPTION,description.getText().toString());
                    startActivity(intent);
                }
            }
        });

    }


    private void getWalletBuyPublicId(String  payerId) {
        viewModel = ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
        viewModel.findWalletByPublicId(payerId).observe(this, new Observer<HomeDto>() {
            @Override
            public void onChanged(@Nullable HomeDto homeDto) {
                if (homeDto!=null){
                    Intent intent = new Intent(RequestMoneyActivity.this,ReqShowWalletInfoActivity.class);
                    intent.putExtra(WALLET_NAME,homeDto.getName());
                    intent.putExtra(PUBLIC_ID,homeDto.getPublicId());
                    intent.putExtra(WALLET_ID,homeDto.getId());
                    intent.putExtra(AMOUNT,cost.getText().toString());
                    intent.putExtra(DESCRIPTION,description.getText().toString());
                    startActivity(intent);
                }
            }
        });

    }




}
