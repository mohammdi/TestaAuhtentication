package com.keyob.payment.gateway.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.activities.HomeActivity;
import com.keyob.payment.gateway.dal.model.Wallet;
import com.keyob.payment.gateway.helper.DataBase.ChangeSelectetWallet;
import com.keyob.payment.gateway.helper.DataBase.FackWalletInfoData;
import com.keyob.payment.gateway.helper.reCycelerViewHandler.HomeWalletListReCycelerView;
import com.keyob.payment.gateway.navigation.adapter.NavigationAdapter;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class NavigationFragment extends Fragment {

    private Context context;
    private NavigationAdapter navigationAdapter;
    private View view;
    private RecyclerView recyclerView;
    private List<Wallet> wallets;

    public static NavigationFragment getInstatnc() {
        NavigationFragment navigationFragment = new NavigationFragment();
        Bundle args = new Bundle();
        navigationFragment.setArguments(args);
        return navigationFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_navigation, container, false);
        recyclerView = view.findViewById(R.id.Nav_Header_Recycle_View);
        wallets = fakeGenerateDate();
        setUpRecyclerView(wallets);
        return view;
    }


    public List<Wallet> fakeGenerateDate() {
        List<Wallet> wallets = new ArrayList<>();
        FackWalletInfoData fackWallets = new FackWalletInfoData();
        wallets = fackWallets.getData(getContext());
        return wallets;
    }

    public void setUpRecyclerView(List<Wallet> walletList) {

        if (walletList.size() > 0) {

            HomeWalletListReCycelerView recyclerAdapter = new HomeWalletListReCycelerView(view.getContext(), walletList);
            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(recyclerAdapter);
        } else {
            Toast.makeText(this.getContext(), "nothing wallet in your account", Toast.LENGTH_SHORT).show();
        }

    }

//    private void replaceFragment(int position)
//    {
//        (HomeFragment)getActivity()).replaceFragment(position);
//        navigationAdapter.setSelected(position);
//    }
}
