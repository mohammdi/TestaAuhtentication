package com.keyob.payment.gateway.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.model.CreateRequestMoneyDto;
import com.keyob.payment.gateway.model.RequestMoneyDto;
import com.keyob.payment.gateway.network.MyURLRepository;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;
import com.squareup.picasso.Picasso;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.AMOUNT;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.DESCRIPTION;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.PAYER_WALLET_ID;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.PUBLIC_ID;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.RESULT;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.WALLET_NAME;

public class ReqShowWalletInfoActivity extends AppCompatActivity {

    private ImageView payerimageWallet;
    private TextView payerWalletName;
    private TextView payerId;
    private TextView amount;
    private TextView desc;
    private Button confirmButton;
    private WalletViewModelNetWork viewModel;
    private String p_id ;
    private String p_wallet_name;
    private Long p_wallet_id;
    private String req_amount;
    private String req_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_show_wallet_info);

        payerimageWallet = findViewById(R.id.req_m_show_image);
        payerId = findViewById(R.id.req_m_show_payerId);
        payerWalletName = findViewById(R.id.req_m_show_wallet_name);
        amount = findViewById(R.id.req_m_show_cost);
        desc = findViewById(R.id.req_m_show_desc);
        confirmButton = findViewById(R.id.req_m_show_confirm);

        Intent intent = getIntent();

        p_id = intent.getStringExtra(PUBLIC_ID);
        p_wallet_name = intent.getStringExtra(WALLET_NAME);
        p_wallet_id = intent.getLongExtra(PAYER_WALLET_ID, 0L);
        req_amount = intent.getStringExtra(AMOUNT);
        req_desc = intent.getStringExtra(DESCRIPTION);

        payerWalletName.setText(p_wallet_name);
        payerId.setText(p_id);
        amount.setText(req_amount);
        desc.setText(req_desc);

        StringBuilder sb = new StringBuilder();
        sb.append(MyURLRepository.GET_LOGO_BY_WALLET_ID);
        sb.append(p_wallet_id);
        Picasso.with(this).load(sb.toString()).into(payerimageWallet);

        SingletonWalletInfo instance = SingletonWalletInfo.getInstance();
        final Long senderWalletId = instance.getId();
        final String senderId = instance.getPublicId();
        final String senderName = instance.getWalletName();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateRequestMoneyDto req = new CreateRequestMoneyDto();
                req.setPayerId(p_id);
                req.setWalletId(senderWalletId);
                req.setPayerName(p_wallet_name);
                req.setPayerWalletId(p_wallet_id);
                req.setAmount(Integer.valueOf(req_amount));
                req.setDescription(req_desc);
                req.setSenderId(senderId);
                req.setSenderName(senderName);
                requestMoneyCallApi(req);
            }
        });
    }




    private void requestMoneyCallApi(CreateRequestMoneyDto request) {
        viewModel = ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
        viewModel.requestMoney(request).observe(this, new Observer<RequestMoneyDto>() {
            @Override
            public void onChanged(@Nullable RequestMoneyDto requestMoneyDto) {
                if (requestMoneyDto!=null){
                    Toast.makeText(ReqShowWalletInfoActivity.this, "request Money send successFully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ReqShowWalletInfoActivity.this,RequestMoneyContainerActivity.class);
                    intent.putExtra(RESULT,true);
                    startActivity(intent);

                }
            }
        });

    }
}
