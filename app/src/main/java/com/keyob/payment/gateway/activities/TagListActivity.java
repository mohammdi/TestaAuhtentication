package com.keyob.payment.gateway.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.helper.reCycelerViewHandler.TagListRecyclerAdapter;
import com.keyob.payment.gateway.model.TagDto;
import com.keyob.payment.gateway.staticRepository.PutExtraKey;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;

import java.util.ArrayList;
import java.util.List;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.MESSAGE;

public class TagListActivity extends AppCompatActivity implements TagListRecyclerAdapter.OnItemClickListener{

    private RecyclerView recyclerView;
    private WalletViewModelNetWork viewModel;
    private ProgressBar progressBar;
    private FloatingActionButton createTagBtn;
    private TagListRecyclerAdapter recyclerAdapter;
    private List<TagDto> tagList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_container);
        recyclerView = findViewById(R.id.tag_recyclerView);
        createTagBtn = findViewById(R.id.tag_create_btn);
        progressBar = findViewById(R.id.tag_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        setUpToolBar();
        setUpFloatActionButton();
        getTagList();

        Intent intent = getIntent();
        String message = intent.getStringExtra(MESSAGE);
        if (message!=null){
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }

    }

    private void getTagList() {
        viewModel = ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
        viewModel.getTagByWalletId(SingletonWalletInfo.getInstance().getId()).observe(this, new Observer<List<TagDto>>() {
            @Override
            public void onChanged(@Nullable List<TagDto> tagDtoList) {
                    tagList = tagDtoList;
                    setupRecyclerView(tagList);
                    progressBar.setVisibility(View.GONE);

            }
        });
    }

    public void setUpToolBar(){
        Toolbar toolbar = findViewById(R.id.tag_list_toolbar);
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

    public void setUpFloatActionButton(){
        createTagBtn = findViewById(R.id.tag_create_btn);
        createTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TagListActivity.this, CreateTagActivity.class);
                startActivity(intent);
            }
        });

    }

    public void setupRecyclerView(List<TagDto> dataList){
        progressBar.setVisibility(View.GONE);
        if (dataList !=null && dataList.size()>=1){
            recyclerAdapter = new TagListRecyclerAdapter(this, dataList);
            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerAdapter.setOnItemClickListener(TagListActivity.this);
            recyclerView.setAdapter(recyclerAdapter);
        }else{
            Toast.makeText(this, "هیچ برچسبی یافت نشد...", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onclick(int position) {
        TagDto tagDto = tagList.get(position);

        Intent intent = new Intent(TagListActivity.this,TagDetailsActivity.class);
        intent.putExtra(PutExtraKey.TAG,tagDto);
        startActivity(intent);
    }
}


