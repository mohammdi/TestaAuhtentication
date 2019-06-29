package com.keyob.payment.gateway.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.model.PerPaymentCorrelationDto;
import com.keyob.payment.gateway.model.RequestMoneyDto;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.PAYMENT_UUID;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.REQUEST_MONEY;

public class ConfirmRequestPaymentActivity extends AppCompatActivity {

//    private ImageView receiverImage;
//    private TextView receiverName;
//    private TextView amount;
//    private TextView receiverId;
    private Button continueBtn;
    private TextView senderPass;
    private WalletViewModelNetWork viewModel;
    private RequestMoneyDto request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_request_payment);

        senderPass=findViewById(R.id.req_perPayment_pass);
//        receiverName=findViewById(R.id.req_perPayment_receiverName);
//        amount=findViewById(R.id.req_perPayment_amount);
//        receiverId=findViewById(R.id.req_perPayment_receiverId);
        senderPass=findViewById(R.id.req_perPayment_pass);
        continueBtn =findViewById(R.id.req_perPayment_btn_payment);

        Intent intent = getIntent();
        request = (RequestMoneyDto)intent.getSerializableExtra(REQUEST_MONEY);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (senderPass.getText().toString().equals(SingletonWalletInfo.getInstance().getPassPayment())){
                    payRequest();
                }else {
                    Toast.makeText(ConfirmRequestPaymentActivity.this, "پسوردی که وارد کرده اید نادرست است !", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    private void payRequest() {
        viewModel = ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
        viewModel.payTheRequest(request, SingletonWalletInfo.getInstance().getId()).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String  perPaymentCorrelationDto) {
                if (perPaymentCorrelationDto != null) {
                    Intent intent = new Intent(ConfirmRequestPaymentActivity.this,ReportRequestPaymentActivity.class);
                    intent.putExtra(PAYMENT_UUID, perPaymentCorrelationDto);
                    startActivity(intent);
                }
            }
        });
    }
}
