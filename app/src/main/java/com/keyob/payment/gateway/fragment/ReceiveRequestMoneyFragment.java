package com.keyob.payment.gateway.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.helper.reCycelerViewHandler.ReceiveRequestMoneyListAdapter;
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


public class ReceiveRequestMoneyFragment extends Fragment implements ReceiveRequestMoneyListAdapter.OnItemClickListener {

    private View view;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private WalletViewModelNetWork viewModel;
    private List<RequestMoneyDto> requestMoneyDtoList = new ArrayList<>();
    private ReceiveRequestMoneyListAdapter recyclerAdapter;
    private Context context;
    private RetrofitApiService apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recieve_request_money, container, false);
        recyclerView = view.findViewById(R.id.receive_req_list_Recycler_view);
        context =  view.getContext();

        receiveRequestMoneyList(SingletonWalletInfo.getInstance().getId());

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                apiService = ApiClient.getInstance().create(RetrofitApiService.class);
                final int adapterPosition = viewHolder.getAdapterPosition();
                Call<Void> call = apiService.deleteRequestByPayer(recyclerAdapter.getRequest(adapterPosition).getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            response.code();
                            recyclerAdapter.removeItem(adapterPosition);
                            List<RequestMoneyDto> itemList = recyclerAdapter.getItemList();
                            recyclerAdapter.notifyListChange(itemList);
                            Toast.makeText(context, "درخواست با موفقیت حذف شد ", Toast.LENGTH_LONG).show();
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

        return view;
    }



    private void receiveRequestMoneyList(Long PayerWalletId) {

        viewModel = ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
        viewModel.requestMoneyListByPeyerWalletId(PayerWalletId).observe(this, new Observer<List<RequestMoneyDto>>() {
            @Override
            public void onChanged(@Nullable List<RequestMoneyDto> requestMoneyDtos) {
                requestMoneyDtoList.addAll(requestMoneyDtos);
                setUpRecyclerView(requestMoneyDtoList);
            }
        });
    }


    public void setUpRecyclerView(List<RequestMoneyDto> requestMoneyList) {

        if (requestMoneyList.size() > 0) {

             recyclerAdapter = new ReceiveRequestMoneyListAdapter(getContext(), requestMoneyList);
            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerAdapter.setOnItemClickListener(ReceiveRequestMoneyFragment.this);
            recyclerView.setAdapter(recyclerAdapter);
        }

    }

    @Override
    public void onclick(int position) {
        RequestMoneyDto request = requestMoneyDtoList.get(position);
        Intent intent = new Intent(ReceiveRequestMoneyFragment.this.getActivity(), DetailsReceiveMoneyActivity.class);
        intent.putExtra(REQUEST_MONEY,request);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
          inflater.inflate(R.menu.request_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.request_delete_all:
                Toast.makeText(context, "delete all request", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
