package com.keyob.payment.gateway.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.model.TagDto;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.MESSAGE;

public class CreateTagActivity extends AppCompatActivity {

    private EditText subject;
    private EditText description;
    private EditText amount;
    private EditText count;
    private Button confirmBtn;
    private Switch priceByPayer;
    private Switch infiniteCount;
    private WalletViewModelNetWork viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tag);
        subject = findViewById(R.id.t_c_subject);
        description = findViewById(R.id.t_c_desc);
        amount = findViewById(R.id.t_c_amount);
        count = findViewById(R.id.t_c_count);
        confirmBtn = findViewById(R.id.t_c_confirm);
        priceByPayer = findViewById(R.id.t_c_PriceByPayer);
        infiniteCount = findViewById(R.id.t_c_InfiniteCount);

        switchStateListener();


        confirmBtnListener();



    }

    private void confirmBtnListener() {

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean result = validateData();
                if (result==false){

                    return;
                }
                TagDto tag = new TagDto();
                tag.setSubject(subject.getText().toString());
                tag.setDescription(description.getText().toString());

//                if (amount.getText().toString().length()>1){
//                    tag.setPrice(Integer.valueOf(amount.getText().toString()));
//                }else {
//                    tag.setPrice(0);
//                }
//
//                if (count.getText().toString().length()>1){
//                    tag.setCount(Integer.valueOf(count.getText().toString()));
//                }else {
//
//                    tag.setCount(0);
//                }

                if (priceByPayer.isChecked()) {
                    tag.setPriceByPayer(true);
                } else {
                    tag.setPrice(Integer.valueOf(amount.getText().toString()));
                    tag.setPriceByPayer(false);
                }

                if (infiniteCount.isChecked()) {
                    tag.setHasInfinitCount(true);
                } else {
                    tag.setCount(Integer.valueOf(count.getText().toString()));
                    tag.setHasInfinitCount(false);
                }

                tag.setWalletId(SingletonWalletInfo.getInstance().getId());
                saveTag(tag, SingletonWalletInfo.getInstance().getId());
            }
        });
    }

    private void switchStateListener() {

        priceByPayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (priceByPayer.isChecked()) {

                    amount.setText(null);
                    amount.setEnabled(false);
                } else {
                    amount.setEnabled(true);
                }
            }
        });

        infiniteCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (infiniteCount.isChecked()) {

                    count.setText(null);
                    count.setEnabled(false);
                } else {

                    count.setEnabled(true);
                }
            }
        });

    }

    private boolean validateData() {
        String desc = description.getText().toString();
        String title = subject.getText().toString();
        String count = this.count.getText().toString();
        boolean priceByPayer =this.priceByPayer.isChecked();
        boolean infiniteCount = this.infiniteCount.isChecked();
        String price = amount.getText().toString();
        Boolean result = true;
        if (priceByPayer==false &&  price ==null){
            Toast.makeText(this, "مبلغ را وارد کنید !!!", Toast.LENGTH_LONG).show();
            result =false;
        }
        if (infiniteCount == false && count == null){
            Toast.makeText(this, "مقدار را وارد کنید !!!", Toast.LENGTH_LONG).show();
            result = false;
        }

        if (subject ==null){
            Toast.makeText(this, "عنوان را وارد کنید !!!", Toast.LENGTH_LONG).show();
            result = false;
        }
        return result;
    }

    private void saveTag(final TagDto tag ,Long walletId) {
        viewModel = ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
        viewModel.createTag(tag,walletId).observe(this, new Observer<TagDto>() {
            @Override
            public void onChanged(@Nullable TagDto tagDto) {

                Intent intent =  new Intent(CreateTagActivity.this,TagListActivity.class);
                if (tagDto != null) {
                    intent.putExtra(MESSAGE,"برچسپ با موفقیت ثبت شد...");
                }else {
                    intent.putExtra(MESSAGE,"خطا در سرور...");
                }
                startActivity(intent);
            }
        });
    }
}
