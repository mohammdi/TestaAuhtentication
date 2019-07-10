package com.keyob.payment.gateway.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Switch;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.fragment.ContactFragment;
import com.keyob.payment.gateway.fragment.HomeFragment;
import com.keyob.payment.gateway.fragment.QRCodePaymentFragment;
import com.keyob.payment.gateway.fragment.WalletManagementFragment;
import com.keyob.payment.gateway.helper.SingletonUserInfo;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.helper.dataBase.DataSharedPrefrence;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.CONTACT_LIST;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.HOME_FRAGMENT;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.MESSAGE;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.TRANSIT;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.WALLET_LIST;

public class HomeActivity extends AppCompatActivity {
    private final Context context = this;
    private WalletViewModelNetWork viewModel;

    HashMap<String, String> selectedWalletAtrr = new HashMap<>();

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_wallet_list:
                    WalletManagementFragment walletManagementFragment = new WalletManagementFragment();
                    FragmentTransaction frag_wallet_Transaction = getSupportFragmentManager().beginTransaction();
                    frag_wallet_Transaction.replace(R.id.content, walletManagementFragment, "wallet List");
                    frag_wallet_Transaction.commit();
                    return true;

                case R.id.navigation_home:
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentTransaction frag_home_transaction = getSupportFragmentManager().beginTransaction();
                    frag_home_transaction.replace(R.id.content, homeFragment, "home fragment");
                    frag_home_transaction.commit();
                    return true;

                case R.id.navigation_contact:
                    ContactFragment contactFragment = new ContactFragment();
                    FragmentTransaction frag_contact_transaction = getSupportFragmentManager().beginTransaction();
                    frag_contact_transaction.replace(R.id.content, contactFragment, "contacts");
                    frag_contact_transaction.commit();
                    return true;

                case R.id.navigation_qr_code_pay:
                    QRCodePaymentFragment payment = new QRCodePaymentFragment();
                    FragmentTransaction frag_QRCode_transaction = getSupportFragmentManager().beginTransaction();
                    frag_QRCode_transaction.replace(R.id.content, payment, "QRCode Payment");
                    frag_QRCode_transaction.commit();
                    return true;
            }

            return false;
        }

    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        Intent intent = getIntent();
        String fragmentName = intent.getStringExtra(TRANSIT);
        String message = intent.getStringExtra(MESSAGE);
        if (fragmentName!=null){

            switch (fragmentName) {
                case WALLET_LIST:
                    goToWalletFragment(message);
                    break;
                case HOME_FRAGMENT:
                    goToHomeFragment(message);
                    break;
                case CONTACT_LIST:
                    goToContactFragment(message);
                    break;
            }

        }else {
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction frag_home_transaction = getSupportFragmentManager().beginTransaction();
            frag_home_transaction.replace(R.id.content, homeFragment, "homefragment");
            frag_home_transaction.commit();
        }
    }


    public void goToWalletFragment(String message) {
        Bundle data = new Bundle();
        data.putString(MESSAGE, message);
        WalletManagementFragment walletManagementFragment = new WalletManagementFragment();
        walletManagementFragment.setArguments(data);
        FragmentTransaction frag_wallet_Transaction = getSupportFragmentManager().beginTransaction();
        frag_wallet_Transaction.replace(R.id.content, walletManagementFragment, "wallet List");
        frag_wallet_Transaction.commit();
    }

    public void goToHomeFragment(String message) {
        Bundle data = new Bundle();
        data.putString(MESSAGE, message);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(data);
        FragmentTransaction frag_home_transaction = getSupportFragmentManager().beginTransaction();
        frag_home_transaction.replace(R.id.content, homeFragment, "homefragment");
        frag_home_transaction.commit();
    }


    public void goToContactFragment(String message) {
        Bundle data = new Bundle();
        data.putString(MESSAGE, message);
        ContactFragment contactFragment = new ContactFragment();
        contactFragment.setArguments(data);
        FragmentTransaction frag_contact_transaction = getSupportFragmentManager().beginTransaction();
        frag_contact_transaction.replace(R.id.content, contactFragment, "contacts");
        frag_contact_transaction.commit();
    }

}
