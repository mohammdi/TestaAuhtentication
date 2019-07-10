package com.keyob.payment.gateway.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.helper.URLAttacher;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.model.TagDto;
import com.keyob.payment.gateway.network.MyURLRepository;
import com.keyob.payment.gateway.staticRepository.PutExtraKey;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;
import com.squareup.picasso.Picasso;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.PAYMENT_UUID;

public class TagPaymentActivity extends AppCompatActivity {

    private TextView walletName;
    private TextView desc;
    private TextView title;
    private TextView txtAmount;
    private TextView txtAmountLable;
    private EditText editTextAmount;
    private EditText editTextPassWord;
    private Button paymentBtn;
    private ImageView profImage;
    private WalletViewModelNetWork viewModel;
    private HomeDto wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_payment);

        walletName = findViewById(R.id.t_p_walletName);
        desc = findViewById(R.id.t_p_desc);
        title = findViewById(R.id.t_p_title);
        txtAmount = findViewById(R.id.t_p_amount);
        txtAmountLable= findViewById(R.id.t_p_amount_label);
        editTextAmount = findViewById(R.id.t_p_editText_amount);
        editTextPassWord = findViewById(R.id.t_p_wallet_password);
        txtAmount = findViewById(R.id.t_p_amount);
        paymentBtn = findViewById(R.id.t_p_payment);
        profImage = findViewById(R.id.t_p_wallet_Image);


        final Intent intent = getIntent();
        final TagDto tag = (TagDto) intent.getSerializableExtra(PutExtraKey.TAG);

        getWalletTagOwner(tag);
        initView(tag);
        setUpToolBar();
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = editTextPassWord.getText().toString();
                if (!pass .equals(SingletonWalletInfo.getInstance().getPassPayment())){
                    Toast.makeText(TagPaymentActivity.this, "رمز پرداخت اشتباه است ", Toast.LENGTH_SHORT).show();
                    return;
                }
                TagDto tagPay = new TagDto();
                tagPay.setId(tag.getId());
                tagPay.setWalletId(tag.getWalletId());
                if (tag.isPriceByPayer()){
                    tagPay.setPrice(Integer.valueOf(editTextAmount.getText().toString()));
                }else {
                    tagPay.setPrice(Integer.valueOf(txtAmount.getText().toString()));
                }

                viewModel.payTag(tagPay, SingletonWalletInfo.getInstance().getId()).observe(TagPaymentActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {

                        if (s!=null){
                            Intent reportIntent = new Intent(TagPaymentActivity.this,ReportRequestPaymentActivity.class);
                            reportIntent.putExtra(PAYMENT_UUID,s);
                            startActivity(reportIntent);
                        }else {
                            Toast.makeText(TagPaymentActivity.this, "خطا در شبکه !!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }

    private void getWalletTagOwner(TagDto tagDto) {

        viewModel = ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
        viewModel.getWalletById(tagDto.getWalletId()).observe(this, new Observer<HomeDto>() {
            @Override
            public void onChanged(@Nullable HomeDto homeDto) {
                if (homeDto!=null){
                    wallet = new HomeDto();
                    wallet = homeDto;
                    walletName.setText(wallet.getName());
                }
            }
        });
    }

    private void initView(TagDto tag) {

        title.setText(tag.getSubject());
        if(tag.isPriceByPayer()){
            txtAmount.setText("");
            txtAmountLable.setText("انتخاب مبلغ با پرداخت کننده :");
        }else {
            editTextAmount.setEnabled(false);
            editTextAmount.setVisibility(View.INVISIBLE);
            txtAmount.setText(String.valueOf(tag.getPrice()));
        }
        desc.setText(tag.getDescription());
        Picasso.with(TagPaymentActivity.this).load(URLAttacher.doAttach(MyURLRepository.GET_LOGO_BY_WALLET_ID,String.valueOf(tag.getWalletId()),null)).into(profImage);
    }

    public void setUpToolBar(){
        Toolbar toolbar = findViewById(R.id.t_p_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setElevation(10);
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.wallet_item_gradient_selector));
    }
}
