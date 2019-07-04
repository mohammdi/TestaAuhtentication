package com.keyob.payment.gateway.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.PersianDateCoordinatore;
import com.keyob.payment.gateway.helper.transform.PrettyShow;
import com.keyob.payment.gateway.model.ResponseCorrelationDto;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.PAYMENT_UUID;

public class ReportRequestPaymentActivity extends AppCompatActivity {

    private final static int splash_count =1000;
    private TextView destinationWallet;
    private TextView sourceWallet;
    private TextView amount;
    private TextView date;
    private TextView status;
    private WalletViewModelNetWork viewModel;
    private ProgressBar progressBar ;
    private Button  confirmReportBtn;
    private  TextView refrenseId;
    private LinearLayout statusBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_request_payment);

        destinationWallet = findViewById(R.id.conf_req_pay_destination);
        sourceWallet = findViewById(R.id.conf_req_pay_source);
        amount = findViewById(R.id.conf_req_pay_amount);
        date = findViewById(R.id.conf_req_pay_date);
        status = findViewById(R.id.conf_req_pay_status);
        progressBar =findViewById(R.id.w_m_progressBar);
        confirmReportBtn = findViewById(R.id.conf_req_pay_confirm_btn);
        refrenseId = findViewById(R.id.conf_req_pay_correlationId);
        statusBar = findViewById(R.id.conf_req_pay_statusBar);


        final Intent intent = getIntent();
        final String correlationId = intent.getStringExtra(PAYMENT_UUID);

        getReport(correlationId);

        confirmReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportRequestPaymentActivity.this,HomeActivity.class);
                startActivity(i);
            }
        });

    }

    private void getReport(final String correlationId) {
        viewModel = ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
        viewModel.reportRequsetPayment(correlationId).observe(this, new Observer<ResponseCorrelationDto>() {
            @Override
            public void onChanged(@Nullable ResponseCorrelationDto responseCorrelationDto) {
                if (responseCorrelationDto != null) {

                    if (responseCorrelationDto.getStatus().equals("Successful")) {

                        statusBar.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.green));
                        status.setText(getApplicationContext().getResources().getString(R.string.successful_persian));
                    } else {

                        statusBar.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.red));
                        status.setText(getApplicationContext().getResources().getString(R.string.unSuccessful_persian));
                    }

                    destinationWallet.setText(PrettyShow.separatedPublicId(responseCorrelationDto.getReceiverId()));
                    sourceWallet.setText(PrettyShow.separatedPublicId(responseCorrelationDto.getSenderId()));
                    amount.setText(PrettyShow.separatedZero(responseCorrelationDto.getAmount()));
                    String persianDate = PersianDateCoordinatore.ConvertGregorianToPersian(responseCorrelationDto.getTransactionDate());
                    date.setText(persianDate);
                    refrenseId.setText(correlationId);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

}
