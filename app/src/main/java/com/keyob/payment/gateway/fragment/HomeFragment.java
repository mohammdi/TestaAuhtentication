package com.keyob.payment.gateway.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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

import com.google.gson.Gson;
import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.activities.RequestMoneyContainerActivity;
import com.keyob.payment.gateway.activities.SelectDatePassBookActivity;
import com.keyob.payment.gateway.activities.TagListActivity;
import com.keyob.payment.gateway.helper.SingletonUserInfo;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.helper.transform.PrettyShow;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.network.AlertFactory;
import com.keyob.payment.gateway.network.InternetStatus;
import com.keyob.payment.gateway.network.MyURLRepository;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;



public class HomeFragment extends Fragment {
    private View view;
    private List<HomeDto> walletList;
    private GridLayout gridLayout;
    private ActionBarDrawerToggle actionBarToggle;
    private WalletViewModelNetWork walletViewModelNetwork;
    private CircularImageView profileHome;
    private TextView balanceHome;
    private TextView walletNameHome;
    private SingletonWalletInfo instance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        gridLayout = view.findViewById(R.id.home_grid_layout);
        profileHome = view.findViewById(R.id.home_wallet_pic);
        walletNameHome = view.findViewById(R.id.home_walletName);
        balanceHome = view.findViewById(R.id.home_balance);

        setSingleEvent(gridLayout);
        setUpToolBar();
        // check internet status
        InternetStatus internetStatus = new InternetStatus(getContext());
        Boolean hasInternet = internetStatus.statusNetWOrk();

        instance = SingletonWalletInfo.getInstance();

                if (!hasInternet) {
                    AlertFactory alertFactory = new AlertFactory(getContext());
                    alertFactory.singleButtonAlert("هشدار", "عدم دسترسی به شبکه!");
                } else
                    if (instance.getId()!= null) {
                    requestWalletByWalletId(walletViewModelNetwork,instance.getId());
                    }else {
                    requestGetWalletByUserId(walletViewModelNetwork);
                }


//            }
        return view;
    }

    private void requestWalletByWalletId(WalletViewModelNetWork viewModel,Long walletId) {

        viewModel = ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
        viewModel.getWalletById(walletId).observe(this, new Observer<HomeDto>() {
            @Override
            public void onChanged(@Nullable HomeDto homeDto) {
                if (homeDto!=null){
                    prepareActionBarInformation(homeDto);
                    instance.setId(homeDto.getId());
                    instance.setWalletToken(homeDto.getWalletToken());
                    instance.setWalletName(homeDto.getName());
                    instance.setBalance(homeDto.getBalance());
                    instance.setBaseLink(homeDto.getBaseLink());
                    instance.setPublicId(homeDto.getPublicId());
                    instance.setPassPayment(homeDto.getPassPayment());

                }
            }
        });


    }

    private Boolean getterArguments() {
        boolean hasResult = false;
        Bundle arguments = this.getArguments();
        if (arguments != null) {
            String wallet = arguments.getString("wallet");

            if (wallet != null) {
                Gson gson = new Gson();
                HomeDto homeDto = gson.fromJson(wallet, HomeDto.class);
                instance.setId(homeDto.getId());
                instance.setWalletToken(homeDto.getWalletToken());
                instance.setWalletName(homeDto.getName());
                instance.setBalance(homeDto.getBalance());
                instance.setBaseLink(homeDto.getBaseLink());
                instance.setPublicId(homeDto.getPublicId());
                instance.setPassPayment(homeDto.getPassPayment());
                prepareActionBarInformation(homeDto);
                hasResult =true;
            }
        }
        return hasResult;
    }

    private void requestWalletImage(String url, Long walletId) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append(walletId);

        Picasso.with(getContext())
                .load(sb.toString())
                .into(profileHome);

    }


    private void requestGetWalletByUserId(WalletViewModelNetWork viewModel) {
        viewModel = ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
        viewModel.getWalletByUserId(Long.valueOf(SingletonUserInfo.getInstance().getId().toString())).observe(this, new Observer<List<HomeDto>>() {
            @Override
            public void onChanged(@Nullable List<HomeDto> wallets) {
                if (wallets != null) {
                    walletList = wallets;
                    for (HomeDto w : walletList) {
                        if (w.getDefault() != null && w.getDefault()) {
                            prepareActionBarInformation(w);
                            instance.setId(w.getId());
                            instance.setWalletToken(w.getWalletToken());
                            instance.setWalletName(w.getName());
                            instance.setBalance(w.getBalance());
                            instance.setBaseLink(w.getBaseLink());
                            instance.setPublicId(w.getPublicId());
                            instance.setPassPayment(w.getPassPayment());
                        }
                    }
                }
            }
        });
    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        final DrawerLayout drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);

        ActionBar actionBar =((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBarToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout, toolbar, 0, 0);
        actionBar.setElevation(10);
        actionBar.setBackgroundDrawable(getActivity().getDrawable(R.drawable.wallet_item_gradient_selector_up_to_botom));
        CollapsingToolbarLayout collapsingToolbar = view.findViewById(R.id.collapsing_toolbar);
        drawerLayout.addDrawerListener(actionBarToggle);
        actionBarToggle.syncState();
        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
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

    public void setSingleEvent(final GridLayout singleEvent) {
        for (int i = 0; i < singleEvent.getChildCount(); i++) {
            final CardView cardView = (CardView) singleEvent.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    switch (cardView.getId()) {
                        case R.id.home_request_money:
                            intent = new Intent(getActivity(), RequestMoneyContainerActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.home_pass_Book:
                            intent = new Intent(getActivity(), SelectDatePassBookActivity.class);
                            startActivity(intent);
                            break;

                        case R.id.home_tag_management:
                            intent=new Intent(getActivity(), TagListActivity.class);
                            startActivity(intent);

                        default:
                            Toast.makeText(getContext(), "click on index" + finalI, Toast.LENGTH_SHORT).show();
                            break;

                    }

                }
            });
        }
    }

    public void prepareActionBarInformation(HomeDto wallet) {

        String prettyBalance = PrettyShow.separatedZero(wallet.getBalance());
        balanceHome.setText(prettyBalance);
        walletNameHome.setText(wallet.getName());
        requestWalletImage(MyURLRepository.GET_LOGO_BY_WALLET_ID, wallet.getId());
        if (profileHome.getDrawable()== null){
            profileHome.setImageDrawable(getResources().getDrawable(R.drawable.ic_profile));
        }
    }
    // daclare user information in drawer navigation
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }
}
