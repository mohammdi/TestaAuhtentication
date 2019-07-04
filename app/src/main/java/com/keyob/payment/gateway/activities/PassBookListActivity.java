package com.keyob.payment.gateway.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.keyob.payment.gateway.Constants;
import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.helper.reCycelerViewHandler.PassBookListRecyclerAdapter;
import com.keyob.payment.gateway.model.FromToDateModel;
import com.keyob.payment.gateway.model.PassBookRequestDto;
import com.keyob.payment.gateway.model.PassBookResponseDto;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.DATE_MODEL;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.MESSAGE;

public class PassBookListActivity extends AppCompatActivity {

     private RecyclerView recyclerView;
     private WalletViewModelNetWork viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_book_list);

        recyclerView = findViewById(R.id.pass_book_Recycle_View);

        final Intent intent = getIntent();
        FromToDateModel model = (FromToDateModel) intent.getSerializableExtra(DATE_MODEL);
        Date fromDate = model.getFromDate();
        Date toDate = model.getToDate();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strToDate = formatter.format(toDate);
        String strFromDate = formatter.format(fromDate);

        PassBookRequestDto pReq= new PassBookRequestDto();
        pReq.setEndDate(strToDate);
        pReq.setStartDate(strFromDate);
        pReq.setSearchType(Constants.REQUEST_ALL_TYPE_PASSBOOK);
        pReq.setWalletId(SingletonWalletInfo.getInstance().getId());
        pReq.setPage(0);

        viewModel = ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
        viewModel.getPassBookList(pReq).observe(this, new Observer<List<PassBookResponseDto>>() {
            @Override
            public void onChanged(@Nullable List<PassBookResponseDto> passBookResponseDtos) {

                if (passBookResponseDtos!=null && passBookResponseDtos.size()>0){
                    setUpRecyclerView(passBookResponseDtos);
                }else {
                    Intent messageIntent = new Intent(PassBookListActivity.this,MessageActivity.class);
                    messageIntent.putExtra(MESSAGE,"هیج تراکنشی وجود ندارد");
                    startActivity(messageIntent);
                }
            }
        });

        setUpToolBar();

    }


    public void setUpRecyclerView(List<PassBookResponseDto> dataList) {

        if (dataList!=null) {
            recyclerView =findViewById(R.id.pass_book_Recycle_View);
            PassBookListRecyclerAdapter recyclerAdapter = new PassBookListRecyclerAdapter(this, dataList);
            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//            recyclerAdapter.setOnItemClickListener(WalletManagementFragment.this);
            recyclerView.setAdapter(recyclerAdapter);
        } else {
            Toast.makeText(this, "nothing wallet in your account", Toast.LENGTH_SHORT).show();
        }

    }


    private void setUpToolBar() {

        Toolbar toolbar = findViewById(R.id.pk_toolbar);
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
        actionBar.setTitle("صفحه اصلی ");
    }

}
