package com.keyob.payment.gateway.activities;


import android.support.v4.app.FragmentTransaction;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.fragment.WalletManagementFragment;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.network.AlertFactory;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;

public class CreateWalletActivity extends AppCompatActivity {


    private EditText walletName;
    private EditText passpayment;
    private Button confirm;
    private TextView repeatPass;
    private WalletViewModelNetWork viewModel;
    private HomeDto wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);

        walletName = findViewById(R.id.c_w_Name);
        confirm = (Button) findViewById(R.id.c_w_confirm);
        passpayment = findViewById(R.id.c_w_pass);
        repeatPass = findViewById(R.id.c_w_confirm_pass);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = walletName.getText().toString();
                String pass = passpayment.getText().toString();
                String repeatPas = repeatPass.getText().toString();
                if (name!= null && pass!=null){
                    if (!pass.equals(repeatPas)){
                        AlertFactory alarm = new AlertFactory(getApplicationContext());
                        alarm.singleButtonAlert("","رمز عبور اشباه است ");
                        return;
                    }

                }else {
                    AlertFactory alarm = new AlertFactory(getApplicationContext());
                    alarm.singleButtonAlert("","اطلاهات ناقص میباشد");
                    return;
                }
                wallet = new HomeDto();
                wallet.setName(name);
                wallet.setPassPayment(pass);
                wallet.setType(1);
                wallet.setAddress("");
                wallet.setUserId(15L);
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
                WalletManagementFragment listView = new WalletManagementFragment();
                Bundle data = new Bundle();

                if (homeDto != null) {
                    message = "بگسک با موفقیت ثبت شد";
                    data.putString("wallet", message);
                    listView.setArguments(data);
                } else {
                    message = "خطا در ثبت بگسک";
                    data.putString("wallet", message);
                    listView.setArguments(data);
                }

                FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.content, listView, "لیست بگسک ها");
                fr.commit();

            }
        });

    }
}
