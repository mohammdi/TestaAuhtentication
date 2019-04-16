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

import com.example.mahmood_mohammadi.testaauhtentication.dal.l.dao.WalletDao;
import com.example.mahmood_mohammadi.testaauhtentication.dal.l.model.Wallet;
import com.example.mahmood_mohammadi.testaauhtentication.R;
import com.example.mahmood_mohammadi.testaauhtentication.dal.l.model.WalletType;
import com.example.mahmood_mohammadi.testaauhtentication.helper.ChangeSelectetWallet;
import com.example.mahmood_mohammadi.testaauhtentication.helper.HomeWalletListReCycelerView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeFragment extends Fragment implements HomeWalletListReCycelerView.ONItemclickListener {
    private View view;
    private RecyclerView recyclerView;
    private List<Wallet> wallets ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.home_wallet_Recycle_View);

        setUpToolBar();
         wallets =  generateData();
         if (wallets.size()>0){
             setUpRecyclerView(wallets);
         }

        return view;
    }

    private List<Wallet> generateData() {

        WalletDao walletDao = new WalletDao(getContext());
        List<Wallet> walletList = new ArrayList<>();
        List<HashMap<String, String> > mapWallets  = walletDao.getWalletList();
        for (HashMap<String ,String> map :mapWallets)
        if (mapWallets.size()>0){
            Wallet wallet = new Wallet();
            WalletType walletType = new WalletType();
            wallet.setId(Long.valueOf(map.get(WalletDao.KEY_WALLET_ID)));
            wallet.setName(map.get(WalletDao.KEY_WALLET_NAME));
            walletType.setId(Long.valueOf(map.get(WalletDao.KEY_WALLET_WALLET_TYPE)));
            if (walletType.getId()==1L) {
                walletType.setName("personal");
            }else{
                walletType.setName("business");
            }
            wallet.setWalletType((walletType));
            wallet.setUserId(Long.valueOf(map.get(WalletDao.KEY_WALLET_WALLET_USER_ID)));
            wallet.setSelected(Boolean.valueOf(map.get(WalletDao.KEY_WALLET_IS_SELECTED)));
            wallet.setDefault(Boolean.valueOf(map.get(WalletDao.Key_WALLET_IS_DEFAULT)));
            wallet.setAddress(map.get(WalletDao.KEY_WALLET_ADDRESS));
            wallet.setBannerPath(map.get(WalletDao.KEY_WALLET_BANNER));
            /*wallet.setWalletAddress(mapWallets.get(WalletDao.KEY_WALLET_ADDRESS)); */   //  fix walletAddress And Address Field in dataBase
            wallet.setPassPayment(map.get(WalletDao.KEY_WALLET_PASS_PAYMENT));
            wallet.setPublicId(map.get(WalletDao.KEY_WALLET_PUBLIC_ID));
            wallet.setCreateDate(map.get(WalletDao.KEY_WALLET_CREATE_DATE));
            wallet.setLogoPath(map.get(WalletDao.KEY_WALLET_LOGO));
            walletList.add(wallet);
        }
        return walletList;
    }


    public void setUpRecyclerView(List<Wallet> walletList) {

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
