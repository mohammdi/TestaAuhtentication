package com.keyob.payment.gateway.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.SingleGeneratedAdapterObserver;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.fragment.HomeFragment;
import com.keyob.payment.gateway.helper.SingletonUserInfo;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.network.ApiClient;
import com.keyob.payment.gateway.network.RetrofitApiService;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetDefaultWalletInfoActivity extends AppCompatActivity {


    private EditText walletName;
    private EditText password;
    private EditText confirmPassword;
    private Button confirmBtn;
    private RetrofitApiService apiService;
    private LinearLayout rootview;
    private WalletViewModelNetWork viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_default_wallet_info);
        apiService = ApiClient.getInstance().create(RetrofitApiService.class);

        walletName = findViewById(R.id.w_name);
        password = findViewById(R.id.w_password);
        confirmPassword = findViewById(R.id.w_repeat_password);
        confirmBtn = findViewById(R.id.w_confirm_btn);
        rootview = findViewById(R.id.w_rootView);



        getWalletRequest(Long.valueOf(SingletonUserInfo.getInstance().getId().toString()));
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = walletName.getText().toString();
                String pass = password.getText().toString();
                String repeatPass = confirmPassword.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Snackbar.make(rootview,"نام بگسک  را وارد نمایید !!!",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Snackbar.make(rootview,"رمز عبور را وارد نمایید !!!",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (!repeatPass.equals(pass)){
                    Snackbar.make(rootview,"رمز عبور نادرست است !!!",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (pass.length() < 5) {
                    Snackbar.make(rootview,"حداقل تعداد رمز عبور 5 کاراکتر است",Snackbar.LENGTH_LONG).show();

                    return;
                }


                HomeDto wallet = SingletonWalletInfo.getInstance().getWallet();
                wallet.setName(name);
                wallet.setPassPayment(pass);
                apiService.updateWallet(wallet.getId(),wallet).enqueue(new Callback<HomeDto>() {
                    @Override
                    public void onResponse(Call<HomeDto> call, Response<HomeDto> response) {
                        if (response.isSuccessful()){
                            SingletonWalletInfo.getInstance().replace(response.body());
                            Intent intent = new Intent(SetDefaultWalletInfoActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }else {
                            Snackbar.make(rootview,"خطا در شبکه !!!",Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<HomeDto> call, Throwable t) {
                        Snackbar.make(rootview,"خطا در شبکه !!!",Snackbar.LENGTH_LONG).show();
                        Intent intent  = new Intent(SetDefaultWalletInfoActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });


        getWalletRequest(Long.valueOf(SingletonUserInfo.getInstance().getId().toString()));


    }

    private void getWalletRequest(Long userId) {
        viewModel = ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
        viewModel.getWalletByUserId(userId).observe(this, new Observer<List<HomeDto>>() {
            @Override
            public void onChanged(@Nullable List<HomeDto> list) {
                for(HomeDto wallet :list){
                    if (wallet.getDefault()){
                        SingletonWalletInfo.getInstance().replace(wallet);
                    }
                }
            }
        });
    }
}
