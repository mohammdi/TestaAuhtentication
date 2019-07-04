package com.keyob.payment.gateway.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.fragment.WalletManagementFragment;
import com.keyob.payment.gateway.helper.PicassoImageDownloader;
import com.keyob.payment.gateway.helper.URLAttacher;
import com.keyob.payment.gateway.helper.reCycelerViewHandler.PassBookListRecyclerAdapter;
import com.keyob.payment.gateway.helper.transform.PrettyShow;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.model.PassBookResponseDto;
import com.keyob.payment.gateway.network.AlertFactory;
import com.keyob.payment.gateway.network.ApiClient;
import com.keyob.payment.gateway.network.RetrofitApiService;
import com.keyob.payment.gateway.staticRepository.PutExtraKey;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.MESSAGE;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.WALLET;

public class WalletDetailActivity extends AppCompatActivity{

    private TextView edit;
    private TextView delete;
    private TextView name;
    private TextView publicId;
    private TextView balance;
    private TextView link;
    private TextView type;
    private ImageView logo;
    private Toolbar toolbar;
    private WalletViewModelNetWork viewModel;
    private RecyclerView recyclerView ;
    private CoordinatorLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detail);

        name =findViewById(R.id.w_d_name);
        publicId =findViewById(R.id.w_d_publicId);
        balance =findViewById(R.id.w_d_balance);
        link =findViewById(R.id.w_d_link);
        type =findViewById(R.id.w_d_type);
        logo =findViewById(R.id.w_d_logo);
        edit =findViewById(R.id.w_d_edit);
        delete =findViewById(R.id.w_d_delete);
        recyclerView = findViewById(R.id.w_d_passbook);
        rootView = findViewById(R.id.w_d_rootView);

        Toolbar toolbar = findViewById(R.id.w_d_toolbar);
        toolbar.setTitle("صفحه اصلی ");
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        final HomeDto wallet= (HomeDto)intent.getSerializableExtra(WALLET);

        publicId.setText(PrettyShow.separatedPublicId(wallet.getPublicId()));
        name.setText(wallet.getName());
        link.setText(URLAttacher.doAttach(wallet.getBaseLink(),wallet.getWalletToken(),null));
        balance.setText(PrettyShow.separatedZero(wallet.getBalance()));
        Picasso.with(getApplicationContext()).load(new File(PicassoImageDownloader.getFileName(wallet.getName()))).into(logo);

        int wType = wallet.getType();
        if (wType==1){

            this.type.setText("تجاری");
        }else{

            this.type.setText("شخصی");
        }

        viewModel= ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
        viewModel.getRecentPassBook(wallet.getId()).observe(this, new Observer<List<PassBookResponseDto>>() {
            @Override
            public void onChanged(@Nullable List<PassBookResponseDto> passBookResponseList) {
                if (passBookResponseList!=null){
                    setUpRecyclerView(passBookResponseList);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = "";
                if(wallet.getBalance()>0){
                    message = "بگسک شما دارای اعتبار میباشد!!!";
                }else {
                    message ="ایا مطمئن هستید ؟";
                }

                Snackbar
                        .make(rootView, message,
                                Snackbar.LENGTH_SHORT)
                        .setAction("بله", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RetrofitApiService apiService = ApiClient.getInstance().create(RetrofitApiService.class);
                                    Call<Void> call = apiService.deleteWallet(wallet.getId());
                                    call.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {

                                            String message = "";
                                            if (response.isSuccessful()){

                                                message ="بگسک شما با موفقیت حذف شد ";
                                            }else {
                                                message = " خطا در سرور";
                                            }
//                                            Bundle data = new Bundle();
//                                            WalletManagementFragment walletManagementFragment = new WalletManagementFragment();
//                                            data.putString(MESSAGE, message);
//                                            walletManagementFragment.setArguments(data);
//                                            HomeActivity h = new HomeActivity();
//                                            h.goToWalletFragment(message);
                                            Intent i = new Intent(WalletDetailActivity.this,HomeActivity.class);
                                            startActivity(i);
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {

                                        }
                                    });
                            }
                        }).show();
            }
        });


    }

    public void setUpRecyclerView(List<PassBookResponseDto> dataList) {
            PassBookListRecyclerAdapter recyclerAdapter = new PassBookListRecyclerAdapter(this, dataList);
            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(recyclerAdapter);

    }

}