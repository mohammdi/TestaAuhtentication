package com.keyob.payment.gateway.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.activities.CreateWalletActivity;
import com.keyob.payment.gateway.activities.WalletDetail;
import com.keyob.payment.gateway.helper.PicassoImageDownloader;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.model.Wallet;
import com.keyob.payment.gateway.helper.reCycelerViewHandler.WalletListRecyclerViewAdapter;
import com.keyob.payment.gateway.model.WalletDto;
import com.keyob.payment.gateway.network.AlertFactory;
import com.keyob.payment.gateway.network.MyURLRepository;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WalletManagementFragment extends Fragment implements WalletListRecyclerViewAdapter.OnItemClickListener {
    private View view;
    private RecyclerView recyclerView;
    private FloatingActionButton create_wallet_btn;
    private List<HomeDto> dataList ;
    private TextView walletName;
    private TextView balanse;
    private ImageView walletImage;
    private TextView publicId;
    private ProgressBar progressBar;
    private WalletViewModelNetWork walletViewModelNetWork;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wallet_managment, container, false);
        // view Binding
        walletImage= view.findViewById(R.id.w_m_wallet_logo);
        walletName = view.findViewById(R.id.w_m_wallet_name);
        publicId = view.findViewById(R.id.w_m_publicId_id);
        progressBar = view.findViewById(R.id.w_m_progressBar);
        progressBar.setVisibility(View.VISIBLE);


        walletViewModelNetWork = ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
        walletViewModelNetWork.getWalletByUserId(1L).observe(this, new Observer<List<HomeDto>>() {
            @Override
            public void onChanged(@Nullable List<HomeDto> wallets) {
                if (wallets!=null){
                    if (wallets.size()>0){
                        dataList =new ArrayList<>();
                        dataList.addAll(wallets);
                        for (HomeDto dto :dataList){
                            PicassoImageDownloader.imageDownload(getContext(),dto.getId(),dto.getPublicId());
                        }
                        setUpRecyclerView(dataList);
                    }
                }
                else{
                    AlertFactory alertFactory = new AlertFactory(getContext());
                    alertFactory.alertFactory(null,"اختلال در سرور ! ");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        setupFloatActionButton(view);
        return view;
    }
    public void setUpRecyclerView(List<HomeDto> walletList) {

        if (walletList!=null) {
            recyclerView =view.findViewById(R.id.wallet_Recycle_View);
            WalletListRecyclerViewAdapter recyclerAdapter = new WalletListRecyclerViewAdapter(getActivity(), walletList);
            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(recyclerAdapter);
            progressBar.setVisibility(View.GONE);
        } else {

            Toast.makeText(this.getContext(), "nothing wallet in your account", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }

    }
    public void setupFloatActionButton(View view) {
        create_wallet_btn = (FloatingActionButton) view.findViewById(R.id.fab);
        create_wallet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletManagementFragment.this.getActivity(), CreateWalletActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onclick(int position) {

        Intent intent = new Intent(this.getContext(), WalletDetail.class);
        HomeDto w = dataList.get(position);

//        intent.putExtra(PutExtraKey.EDIT, true);
//        intent.putExtra(PutExtraKey.WALLET_NAME, w.getName());
//        intent.putExtra(PutExtraKey.PUBLIC_ID, w.getPublicId());
//        intent.putExtra(PutExtraKey.WALLET_ID, w.getId());
//        intent.putExtra(PutExtraKey.WALLET_TYPE ,w.getType());
//        intent.putExtra(PutExtraKey.WALLET_PASS, w.getPassPayment());
        startActivity(intent);
    }


}
