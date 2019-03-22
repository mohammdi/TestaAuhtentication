package com.example.mahmood_mohammadi.testaauhtentication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mahmood_mohammadi.testaauhtentication.ObjectModel.Wallet;
import com.example.mahmood_mohammadi.testaauhtentication.R;
import com.example.mahmood_mohammadi.testaauhtentication.helper.ChangeSelectetWallet;
import com.example.mahmood_mohammadi.testaauhtentication.helper.FackWalletInfoData;
import com.example.mahmood_mohammadi.testaauhtentication.helper.HomeWalletListReCycelerView;

import org.json.JSONException;

import java.util.List;


public class HomeFragment extends Fragment implements HomeWalletListReCycelerView.ONItemclickListener {
    private View view;
    private RecyclerView recyclerView;
    private List<Wallet> wallets ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView =(RecyclerView)view.findViewById(R.id.home_wallet_Recycle_View);

        setUpToolBar();
         wallets = generateData();
        setUprecyclerView(wallets);

        return view;
    }

    private List<Wallet> generateData() {

        FackWalletInfoData fackWalletInfoData =  new FackWalletInfoData();
        return fackWalletInfoData.getData(this.getContext());
    }


    public void setUprecyclerView(List<Wallet> walletList) {

        if (walletList.size() > 0) {

            HomeWalletListReCycelerView recyclerAdapter = new HomeWalletListReCycelerView(view.getContext(), walletList);
            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false );
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(recyclerAdapter);

        } else {
            Toast.makeText(this.getContext(), "nothing wallet in your account", Toast.LENGTH_SHORT).show();
        }

    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        ActionBarDrawerToggle actionBarToggle = new ActionBarDrawerToggle(getActivity(),
                drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(actionBarToggle);
        actionBarToggle.syncState();
    }


    @Override
    public void onclik(int position) {

        Wallet wallet = wallets.get(position);
        ChangeSelectetWallet selectetWallet= new ChangeSelectetWallet();
        try {
            selectetWallet.changdefaultWallet(this.getContext(),wallet);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
