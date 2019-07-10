package com.keyob.payment.gateway.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.URLAttacher;
import com.keyob.payment.gateway.model.TagDto;
import com.keyob.payment.gateway.network.ApiClient;
import com.keyob.payment.gateway.network.MyURLRepository;
import com.keyob.payment.gateway.network.RetrofitApiService;
import com.keyob.payment.gateway.staticRepository.PutExtraKey;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.MESSAGE;

public class TagDetailsActivity extends AppCompatActivity {

    private EditText title;
    private EditText desc;
    private EditText count;
    private EditText amount;
    private TextView tagLink;
    private TextView shareQrCodeTxt;
    private TextView totalPayReceived;
    private TextView paymentCounts;
    private Button updateTag;
    private ImageView tagQrCode;
    private ImageView shareTagLink;
    private Switch priceByPayer;
    private Switch infiniteCount;
    private WalletViewModelNetWork viewModel;
    private TagDto tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_details);
        title = findViewById(R.id.t_d_title);
        count = findViewById(R.id.t_d_count);
        amount = findViewById(R.id.t_d_amount);
        tagLink = findViewById(R.id.t_d_link);
        shareQrCodeTxt = findViewById(R.id.t_d_qrCode_share);
        updateTag = findViewById(R.id.t_d_confirm_update);
        tagQrCode = findViewById(R.id.t_d_qrCode);
        shareTagLink = findViewById(R.id.t_d_link_share);
        desc = findViewById(R.id.t_d_desc);
        paymentCounts = findViewById(R.id.t_d_pay_counts);
        totalPayReceived = findViewById(R.id.t_d_total_pay_amount);
        priceByPayer = findViewById(R.id.t_d_PriceByPayer);
        infiniteCount = findViewById(R.id.t_d_InfiniteCount);

        Intent intent = getIntent();
        tag = (TagDto) intent.getSerializableExtra(PutExtraKey.TAG);

        priceByPayerListener();
        hasinfintListener();
        initView(tag);
        setupToolbar();
        conFirmBtnListener();

    }

    private TagDto fetchData() {
        TagDto tempTag = new TagDto();
        tempTag.setId(tag.getId());
        tempTag.setWalletId(tag.getWalletId());
        tempTag.setBaseLink(tag.getBaseLink());
        tempTag.setTagToken(tag.getTagToken());
        tempTag.setCreateDate(tag.getCreateDate());
        tempTag.setDeleted(tag.isDeleted());
        tempTag.setTotalPayments(tag.getTotalPayments());
        tempTag.setPaymentsCount(tag.getPaymentsCount());
        tempTag.setActive(tag.getActive());

        if (priceByPayer.isChecked()) {

            tempTag.setPrice(0);
        } else {
            tempTag.setPrice(Integer.valueOf(amount.getText().toString()));
        }

        tempTag.setPriceByPayer(priceByPayer.isChecked());
        tempTag.setHasInfinitCount(infiniteCount.isChecked());

        if (infiniteCount.isChecked()) {
            tempTag.setCount(0);
        } else {
            tempTag.setCount(Integer.valueOf(count.getText().toString()));
        }

        tempTag.setDescription(desc.getText().toString());
        tempTag.setSubject(title.getText().toString());
        return tempTag;
    }

    private void conFirmBtnListener() {
        updateTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagDto tagDto = fetchData();
                if (tagDto.getSubject() == null && tagDto.getSubject().length() < 1) {

                    Toast.makeText(TagDetailsActivity.this, "عنوان را وارد کنید!!!", Toast.LENGTH_LONG).show();

                }else{

                    viewModel = ViewModelProviders.of(TagDetailsActivity.this).get(WalletViewModelNetWork.class);
                    viewModel.updateTag(tagDto).observe(TagDetailsActivity.this, new Observer<TagDto>() {
                        @Override
                        public void onChanged(@Nullable TagDto tagDto) {
                            Intent intent = new Intent(TagDetailsActivity.this, TagListActivity.class);
                            if (tagDto != null) {
                                intent.putExtra(MESSAGE, "برچسپ با موفقیت ویرایش شد...");
                            } else {
                                intent.putExtra(MESSAGE, "خطا در سرور...");
                            }
                            startActivity(intent);
                        }
                    });

                }
            }
        });
    }

    private void priceByPayerListener() {
        priceByPayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (priceByPayer.isChecked()) {
                    amount.setEnabled(false);
                    amount.setText("");
                    amount.setHint("توسط کاربر");

                } else {
                    amount.setEnabled(true);
                    amount.setText(String.valueOf(tag.getPrice()));
                }

            }
        });
    }


    private void hasinfintListener() {
        infiniteCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (infiniteCount.isChecked()) {
                    count.setEnabled(false);
                    count.setText("");
                    count.setHint("بینهایت");
                } else {
                    count.setEnabled(true);
                    count.setHint("");
                    count.setText(String.valueOf(tag.getCount()));

                }
            }
        });
    }

    private void initView(TagDto tag) {

        title.setText(tag.getSubject());
        if (tag.isPriceByPayer()) {
            priceByPayer.setChecked(true);
            amount.setEnabled(false);
            amount.setHint("توسط کاربر");
        } else {
            amount.setText(String.valueOf(tag.getPrice()));

        }
        if (tag.getHasInfinitCount()) {
            infiniteCount.setChecked(true);
            count.setEnabled(false);
            count.setHint("بینهابت");
        }else{

            count.setText(String.valueOf(tag.getCount()));
        }


        tagLink.setText(URLAttacher.doAttach(tag.getBaseLink(), tag.getTagToken(), null));
        Picasso.with(this).load(URLAttacher.doAttach(MyURLRepository.GET_TAG_QR_CODE, String.valueOf(tag.getId()), null)).into(tagQrCode);
        desc.setText(tag.getDescription());
        paymentCounts.setText(String.valueOf(tag.getPaymentsCount()));
        totalPayReceived.setText(String.valueOf(tag.getTotalPayments()));
    }


    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.t_d_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setElevation(10);
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.wallet_item_gradient_selector));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tag_menu ,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.delete_tag :
                    RetrofitApiService apiService = ApiClient.getInstance().create(RetrofitApiService.class);
                    apiService.deleteTag(tag.getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Intent intent = new Intent(TagDetailsActivity.this, TagListActivity.class);
                        if (response.isSuccessful()){
                                intent.putExtra(MESSAGE, "برچسپ با موفقیت حذف شد...");
                            } else {
                                intent.putExtra(MESSAGE, "خطا در سرور...");
                            }
                            startActivity(intent);
                        }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                        Intent intent = new Intent(TagDetailsActivity.this, TagListActivity.class);
                        intent.putExtra(MESSAGE, "خطا در سرور...");
                        startActivity(intent);
                    }
                });
                 return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
