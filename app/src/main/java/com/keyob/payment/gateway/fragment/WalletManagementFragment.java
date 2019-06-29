package com.keyob.payment.gateway.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.activities.CreateWalletActivity;
import com.keyob.payment.gateway.helper.PicassoImageDownloader;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.helper.reCycelerViewHandler.WalletListRecyclerViewAdapter;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.network.AlertFactory;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;

import java.util.ArrayList;
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
        walletImage= view.findViewById(R.id.w_m_logo);
        walletName = view.findViewById(R.id.w_m__name);
        publicId = view.findViewById(R.id.w_m_publicId);
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
                            PicassoImageDownloader.imageDownload(getContext(),dto.getId(),dto.getName());
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
            recyclerAdapter.setOnItemClickListener(WalletManagementFragment.this);
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

        HomeFragment homeFragment = new HomeFragment();
        Bundle data =new Bundle();
        HomeDto w = dataList.get(position);
        w.setSelected(true);

        Gson gson = new Gson();
        String json = gson.toJson(w);
        data.putString("wallet",json);
        homeFragment.setArguments(data);

        SingletonWalletInfo instance = SingletonWalletInfo.getInstance();
        instance.setBaseLink(w.getBaseLink());
        instance.setBalance(w.getBalance());
        instance.setWalletName(w.getName());
        instance.setId(w.getId());
        instance.setWalletToken(w.getWalletToken());
        instance.setPublicId(w.getPublicId());
        instance.setPassPayment(w.getPassPayment());

        FragmentTransaction fr = getFragmentManager().beginTransaction();
        fr.replace(R.id.content, homeFragment, "homefragment");
        fr.commit();
    }


}