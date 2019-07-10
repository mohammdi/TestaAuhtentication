package com.keyob.payment.gateway.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.RequestMoneyViewPagerAdapter;
import com.keyob.payment.gateway.fragment.ReceiveRequestMoneyFragment;
import com.keyob.payment.gateway.fragment.RequestMoneyListFragment;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;

public class RequestMoneyContainerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WalletViewModelNetWork viewModel;
    private FloatingActionButton createRequestButton;
    private ProgressBar progressBar;
    private TabLayout tabLayout;
    private ViewPager viewPger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_money_container);
        viewPger = findViewById(R.id.request_money_view_pager);
        tabLayout = findViewById(R.id.request_money_tab);
        RequestMoneyViewPagerAdapter adapter = new RequestMoneyViewPagerAdapter(this.getSupportFragmentManager());
        adapter.addFragment(new RequestMoneyListFragment(),"ارسال شده");
        adapter.addFragment(new ReceiveRequestMoneyFragment()," دریافت شده");
        viewPger.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPger);

        setUpToolBar();
    }

    private void setUpToolBar() {

        Toolbar toolbar = findViewById(R.id.request_list_toolbar);
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
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.wallet_item_gradient_selector));
    }

}
