package com.keyob.payment.gateway.activities.hiddenActivity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.keyob.payment.gateway.Constans;
import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.activities.WalletInfoActivity;
import com.keyob.payment.gateway.fragment.DetailsReceiveMoneyActivity;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.model.QrCodeScanResponseDto;
import com.keyob.payment.gateway.model.RequestMoneyDto;
import com.keyob.payment.gateway.network.ApiClient;
import com.keyob.payment.gateway.network.RetrofitApiService;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;

import java.util.UUID;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.REQUEST_MONEY;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.WALLET;

public class PayProxyActivity extends AppCompatActivity {

    private WalletViewModelNetWork viewModel;
    private RequestMoneyDto request;
    private ProgressBar progressBar;
    private  QrCodeScanResponseDto qrCodeScanner ;
    private RetrofitApiService apiService;
    private QrCodeScanResponseDto qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_proxy);
        progressBar = findViewById(R.id.proxy_progressBar);

        final Intent intent = getIntent();
        QrCodeScanResponseDto qrType = (QrCodeScanResponseDto) intent.getSerializableExtra("QrType");
        if (qrType != null) {
            apiService = ApiClient.getInstance().create(RetrofitApiService.class);
            if (qrType.getType().equals(Constans.QRCODE_REQUEST_TYPE)){

                final UUID requestId = UUID.fromString(qrType.getTargetId());

                viewModel = ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
                viewModel.getRequestById(requestId).observe(this, new Observer<RequestMoneyDto>() {
                    @Override
                    public void onChanged(@Nullable RequestMoneyDto response) {
                        if (response != null) {
                            request = new RequestMoneyDto();
                            request.setId(response.getId());
                            request.setWalletId(response.getWalletId());
                            request.setPayerId(response.getPayerId());
                            request.setSenderId(response.getSenderId());
                            request.setPayerName(response.getPayerName());
                            request.setSenderName(response.getSenderName());
                            request.setRequestToken(response.getRequestToken());
                            request.setStatus(response.getStatus());
                            request.setBaseLink(response.getBaseLink());
                            request.setAmount(response.getAmount());
                            request.setCreateDate(response.getCreateDate());
                            request.setDeleted(response.getDeleted());
                            request.setDescription(response.getDescription());
                            request.setDeletedByPayer(response.getDeletedByPayer());
                            Log.i("RETROFIT",request.toString());

                            Intent i= new Intent(getApplicationContext(),DetailsReceiveMoneyActivity.class);
                            i.putExtra(REQUEST_MONEY,request);
                            startActivity(i);
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });


            }
            else if(qrType.getType().equals(Constans.QRCODE_WALLET_TYPE)){

                viewModel= ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
                viewModel.getWalletById(Long.valueOf(qrType.getTargetId())).observe(this, new Observer<HomeDto>() {
                    @Override
                    public void onChanged(@Nullable HomeDto homeDto) {
                        if (homeDto != null) {
                            Intent walletIntent = new Intent(PayProxyActivity.this, WalletInfoActivity.class);
                            walletIntent.putExtra(WALLET,homeDto);
                            progressBar.setVisibility(View.GONE);
                            startActivity(walletIntent);
                        }
                    }
                });

            }else if (qrType.getType().equals(Constans.QRCODE_TAG_TYPE)){
                //todo
                progressBar.setVisibility(View.GONE);
            }

        }
    }
}
