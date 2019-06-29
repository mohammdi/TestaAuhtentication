package com.keyob.payment.gateway.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.activities.ConfirmRequestPaymentActivity;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.helper.URLAttacher;
import com.keyob.payment.gateway.model.RequestMoneyDto;
import com.keyob.payment.gateway.network.AlertFactory;
import com.keyob.payment.gateway.network.MyURLRepository;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;
import com.squareup.picasso.Picasso;

import java.util.regex.Pattern;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.REQUEST_MONEY;

public class DetailsReceiveMoneyActivity extends AppCompatActivity {


    private TextView walletName;
    private TextView publicId_phoneNumber;
    private TextView amount;
    private TextView description;
    private Button paymentBtn;
    private RequestMoneyDto request;
    private WalletViewModelNetWork viewModel;
    private ImageView senderImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_response_money);

        senderImg = findViewById(R.id.receive_m_d_receiver_image);
        walletName = findViewById(R.id.receive_m_d_wallet_name);
        publicId_phoneNumber = findViewById(R.id.receive_m_d_accountId);
        amount = findViewById(R.id.receive_m_d_amount);
        description = findViewById(R.id.receive_m_d_desc);
        paymentBtn = findViewById(R.id.receive_m_d_payment);

        final Intent intent = getIntent();
        request = (RequestMoneyDto) intent.getSerializableExtra(REQUEST_MONEY);

        dataBinder(request);
        if (!request.getStatus()) {
            paymentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SingletonWalletInfo.getInstance().getBalance()< request.getAmount()){
                        Toast.makeText(DetailsReceiveMoneyActivity.this, "موجودی شما کافی نمیباشد!!!", Toast.LENGTH_LONG).show();
                    }else {
                        Intent i = new Intent(DetailsReceiveMoneyActivity.this, ConfirmRequestPaymentActivity.class);
                        i.putExtra(REQUEST_MONEY, request);
                        startActivity(i);
                    }
                }
            });
        } else {
            paymentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DetailsReceiveMoneyActivity.this, "این در خواست قبلا پرداخت شده ", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void dataBinder(RequestMoneyDto request) {
        walletName.setText(request.getSenderName());
        publicId_phoneNumber.setText(request.getSenderId());
        amount.setText(String.valueOf(request.getAmount()));
        description.setText(request.getDescription());

        String regex = "^(\\+98|0)?9\\d{9}$";
        boolean isPhoneNumber = Pattern.matches(regex, request.getSenderId());
        if (isPhoneNumber) {
            String url = URLAttacher.doAttach(MyURLRepository.GET_LOGO_BY_PHONE_NUMBER, String.valueOf(request.getSenderId()), null);
            Picasso.with(this).load(url).into(senderImg);
        } else {
            String url = URLAttacher.doAttach(MyURLRepository.GET_LOGO_BY_PUBLIC_ID, String.valueOf(request.getSenderId()), null);
            Picasso.with(this).load(url).into(senderImg);
        }

    }


}
