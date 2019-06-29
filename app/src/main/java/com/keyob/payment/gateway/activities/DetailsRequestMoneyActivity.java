package com.keyob.payment.gateway.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.URLAttacher;
import com.keyob.payment.gateway.helper.transform.PrettyShow;
import com.keyob.payment.gateway.model.RequestMoneyDto;
import com.keyob.payment.gateway.network.MyURLRepository;
import com.squareup.picasso.Picasso;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.REQUEST_MONEY;

public class DetailsRequestMoneyActivity extends AppCompatActivity {


    private ImageView qrCode;
    private TextView walletName;
    private TextView publicId_phoneNumber;
    private TextView amount;
    private TextView description;
    private TextView link;
    private TextView textStatus;
    private RequestMoneyDto request;
    private ImageView share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_request_mony);

        share = findViewById(R.id.r_m_d_share);
        qrCode = findViewById(R.id.r_m_d_QrCode);
        walletName = findViewById(R.id.r_m_d_wallet_name);
        publicId_phoneNumber = findViewById(R.id.r_m_d_accountId);
        amount = findViewById(R.id.r_m_d_amount);
        description = findViewById(R.id.r_m_d_desc);
        link = findViewById(R.id.r_m_d_link);
        textStatus = findViewById(R.id.r_m_d_text_status);

        Intent intent = getIntent();
        request = (RequestMoneyDto) intent.getSerializableExtra(REQUEST_MONEY);

        dataBinder(request);

    }

    private void dataBinder(RequestMoneyDto request) {

        walletName.setText(request.getPayerName());
        publicId_phoneNumber.setText(request.getPayerId());

        if (request.getStatus()) {
            textStatus.setText(R.string.Paid_persian);
            textStatus.setTextColor(this.getResources().getColor(R.color.bg_login));

        } else {
            textStatus.setText(R.string.not_paid_persian);
            textStatus.setTextColor(this.getResources().getColor(R.color.btn_logut_bg));
        }
        String api = URLAttacher.doAttach(MyURLRepository.GET_REQUEST_QR_CODE, String.valueOf(request.getId()), null);
        Picasso.with(this).load(api).into(qrCode);
        amount.setText(PrettyShow.separatedZero(request.getAmount()));
        String baseLink = URLAttacher.doAttach(request.getBaseLink(), request.getRequestToken(), null);
        link.setText(baseLink);
        description.setText(request.getDescription());

    }
}
