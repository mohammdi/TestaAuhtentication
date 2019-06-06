package com.keyob.payment.gateway.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.dal.dao.WalletDao;
import com.keyob.payment.gateway.dal.model.Wallet;
import com.keyob.payment.gateway.dal.model.WalletType;
import com.keyob.payment.gateway.helper.DataBase.ChangeSelectetWallet;
import com.keyob.payment.gateway.helper.DataBase.FackWalletInfoData;
import com.keyob.payment.gateway.helper.reCycelerViewHandler.HomeWalletListReCycelerView;
import com.keyob.payment.gateway.navigation.Constants;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeFragment extends Fragment  {
    private View view;
    private RecyclerView recyclerView;
    private List<Wallet> wallets ;
    private GridLayout gridLayout;
    private  ActionBarDrawerToggle actionBarToggle;
    private TextView tagLink;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        gridLayout= view.findViewById(R.id.home_grid_layout);
        tagLink= (TextView)view.findViewById(R.id.home_action_tag);
        tagLink.setHovered(true);
        setSingleEvent(gridLayout);
        setUpToolBar();
        return view;
    }

    public  List<Wallet> generateData() {

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
//            wallet.setWalletType((walletType));
            wallet.setUserId(Long.valueOf(map.get(WalletDao.KEY_WALLET_WALLET_USER_ID)));
            wallet.setSelected(Boolean.valueOf(map.get(WalletDao.KEY_WALLET_IS_SELECTED)));
            wallet.setDefault(Boolean.valueOf(map.get(WalletDao.Key_WALLET_IS_DEFAULT)));
            wallet.setAddress(map.get(WalletDao.KEY_WALLET_ADDRESS));
//            wallet.setBannerPath(map.get(WalletDao.KEY_WALLET_BANNER));
            /*wallet.setWalletAddress(mapWallets.get(WalletDao.KEY_WALLET_ADDRESS)); */   //  fix walletAddress And Address Field in dataBase
            wallet.setPassPayment(map.get(WalletDao.KEY_WALLET_PASS_PAYMENT));
            wallet.setPublicId(map.get(WalletDao.KEY_WALLET_PUBLIC_ID));
            wallet.setCreateDate(map.get(WalletDao.KEY_WALLET_CREATE_DATE));
//            wallet.setLogoPath(map.get(WalletDao.KEY_WALLET_LOGO));
            walletList.add(wallet);
        }
        return walletList;
    }


    public List<Wallet> fackeGenerateDate(){
        List<Wallet> wallets = new ArrayList<>();
        FackWalletInfoData  fackWallets = new FackWalletInfoData();
        wallets =fackWallets.getData(getContext());
        return wallets ;
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

        final DrawerLayout drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
       actionBarToggle = new ActionBarDrawerToggle(getActivity(),
                drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(actionBarToggle);
        actionBarToggle.syncState();
        NavigationView navigationView = (NavigationView)view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

               switch (menuItem.getItemId()) {
                   case R.id.nav_setting:
                       Toast.makeText(getContext(), "perssed _ Setting ", Toast.LENGTH_SHORT).show();
                       drawerLayout.closeDrawer(GravityCompat.START);
                       break;
                   case R.id.nav_share:
                       Toast.makeText(getContext(), "pressed _ share", Toast.LENGTH_SHORT).show();
                       drawerLayout.closeDrawer(GravityCompat.START);
                       break;
                   case R.id.nav_wallet_create:
                       Toast.makeText(getContext(), "pressed _ wallet creation", Toast.LENGTH_SHORT).show();
                       drawerLayout.closeDrawer(GravityCompat.START);
                       break;
                   case R.id.nav_profile:
                       Toast.makeText(getContext(), "pressed _ profile", Toast.LENGTH_SHORT).show();
                       drawerLayout.closeDrawer(GravityCompat.START);
                       break;
                   case R.id.nav_about_us:
                       Toast.makeText(getContext(), "pressed _ about us", Toast.LENGTH_SHORT).show();
                       drawerLayout.closeDrawer(GravityCompat.START);
                       break;
                   case R.id.nav_support:
                       Toast.makeText(getContext(), "pressed _ support", Toast.LENGTH_SHORT).show();
                       drawerLayout.closeDrawer(GravityCompat.START);
                       break;
                   case R.id.nav_feedback:
                       Toast.makeText(getContext(), "pressed _ feedBack", Toast.LENGTH_SHORT).show();
                       drawerLayout.closeDrawer(GravityCompat.START);
                       break;
               }
                return true;
            }
        });
    }

    public void setSingleEvent(GridLayout singleEvent) {
        for (int i = 0; i < singleEvent.getChildCount(); i++) {
            CardView cardView = (CardView) singleEvent.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "click on index"+ finalI, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
