package com.keyob.payment.gateway.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.activities.DetailsRequestMoneyActivity;
import com.keyob.payment.gateway.activities.RequestMoneyActivity;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.helper.reCycelerViewHandler.RequestMoneyListRecyclerAdapter;
import com.keyob.payment.gateway.model.RequestMoneyDto;
import com.keyob.payment.gateway.network.ApiClient;
import com.keyob.payment.gateway.network.RetrofitApiService;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.keyob.payment.gateway.staticRepository.PutExtraKey.REQUEST_MONEY;


public class RequestMoneyListFragment extends Fragment implements RequestMoneyListRecyclerAdapter.OnItemClickListener{

    private View view ;
    private FloatingActionButton createRequestButton;
    private ProgressBar progressBar;
    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private WalletViewModelNetWork viewModel;
    private List<RequestMoneyDto> requestMoneyDtoList = new ArrayList<>();
    private RetrofitApiService apiService;
    private Context context;
    private RequestMoneyListRecyclerAdapter recyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_request_money_list, container, false);
        recyclerView = view.findViewById(R.id.request_list_Recycler_view);
        progressBar = view.findViewById(R.id.request_list_progressBar);
        progressBar.setVisibility(View.GONE);
        context= this.getContext();
        requestMoneyList(SingletonWalletInfo.getInstance().getId());
        setupFloatActionButton();

        deleteRequestBySwip();
        return  view;
    }

    private void deleteRequestBySwip() {

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                apiService = ApiClient.getInstance().create(RetrofitApiService.class);
                final int adapterPosition = viewHolder.getAdapterPosition();
                Call<Void> call = apiService.deleteRequestBySender(recyclerAdapter.getRequest(viewHolder.getAdapterPosition()).getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            response.code();
                            recyclerAdapter.removeItem(adapterPosition);
                            List<RequestMoneyDto> itemList = recyclerAdapter.getItemList();
                            recyclerAdapter.notifyListChange(itemList);
                            Toast.makeText(context, "درخواست با موفقیت حذف شد ", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "مشکل در سرور", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context, "مشکل در سرور", Toast.LENGTH_LONG).show();
                    }
                });

            }
        }).attachToRecyclerView(recyclerView);

    }


    private void requestMoneyList(Long walletId) {
        progressBar.setVisibility(view.VISIBLE);
        viewModel = ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
        viewModel.RequestMoneyGetByWalletId(walletId).observe(this, new Observer<List<RequestMoneyDto>>() {
            @Override
            public void onChanged(@Nullable List<RequestMoneyDto> requestMoneyDtos) {
                requestMoneyDtoList.addAll(requestMoneyDtos);
                setUpRecyclerView(requestMoneyDtoList);
            }
        });
    }

    public void setupFloatActionButton() {
        createRequestButton = view.findViewById(R.id.request_list_fab);
        createRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequestMoneyListFragment.this.getActivity(), RequestMoneyActivity.class);
                startActivity(intent);
            }
        });
    }


    public void setUpRecyclerView(List<RequestMoneyDto> requestMoneyList) {

        if (requestMoneyList.size() > 0) {

            recyclerAdapter = new RequestMoneyListRecyclerAdapter(getContext(), requestMoneyList);
            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerAdapter.setOnItemClickListener(RequestMoneyListFragment.this);
            recyclerView.setAdapter(recyclerAdapter);
            progressBar.setVisibility(view.GONE);
        } else {
            Toast.makeText(getContext(), "nothing wallet in your account", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(view.GONE);

        }

    }

    @Override
    public void onclick(int position) {

        RequestMoneyDto request = requestMoneyDtoList.get(position);
        Intent intent = new Intent(RequestMoneyListFragment.this.getActivity(), DetailsRequestMoneyActivity.class);
        intent.putExtra(REQUEST_MONEY,request);
        startActivity(intent);

    }
}
