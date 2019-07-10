package com.keyob.payment.gateway.activities;


import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.fragment.WalletManagementFragment;
import com.keyob.payment.gateway.helper.SingletonUserInfo;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.network.AlertFactory;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.MESSAGE;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.TRANSIT;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.WALLET_LIST;

public class CreateWalletActivity extends AppCompatActivity {


    private EditText walletName;
    private EditText passpayment;
    private Button confirm;
    private TextView repeatPass;
    private WalletViewModelNetWork viewModel;
    private HomeDto wallet;
    private RelativeLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);

        walletName = findViewById(R.id.c_w_Name);
        confirm = findViewById(R.id.c_w_confirm);
        passpayment = findViewById(R.id.c_w_pass);
        repeatPass = findViewById(R.id.c_w_confirm_pass);
        rootView = findViewById(R.id.c_w_rootView);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = walletName.getText().toString();
                String pass = passpayment.getText().toString();
                String repeatPas = repeatPass.getText().toString();
                if (name != null && pass != null) {
                    Snackbar.make(rootView, "لطفا اطلاعات را وارد نمایید", 3000).show();
                }
                if (!pass.equals(repeatPas)) {
                    Snackbar.make(rootView, "رمز پرداخت اشتباه است", 3000).show();
                    return;
                }
                if (pass.length() < 5) {
                    Snackbar.make(rootView, "حداقل تعداد رمز عبور 5 کاراکتر است", 3000).show();
                    return;
                }
                wallet = new HomeDto();
                wallet.setName(name);
                wallet.setPassPayment(pass);
                wallet.setType(1);
                wallet.setAddress("");
                wallet.setUserId(Long.valueOf(SingletonUserInfo.getInstance().getId().toString()));
                createWalletRequest();
            }
        });
    }


    private void createWalletRequest() {
        viewModel = ViewModelProviders.of(CreateWalletActivity.this).get(WalletViewModelNetWork.class);
        viewModel.createWallet(wallet).observe(CreateWalletActivity.this, new Observer<HomeDto>() {
            @Override
            public void onChanged(@Nullable HomeDto homeDto) {

                String message = "";
                if (homeDto != null) {
                    message = "بگسک با موفقیت ثبت شد";
                } else {
                    message = "خطا در ثبت بگسک";
                }

                Intent intent = new Intent(CreateWalletActivity.this, HomeActivity.class);
                intent.putExtra(TRANSIT, WALLET_LIST);
                intent.putExtra(MESSAGE, message);
                startActivity(intent);
            }
        });

    }
}
