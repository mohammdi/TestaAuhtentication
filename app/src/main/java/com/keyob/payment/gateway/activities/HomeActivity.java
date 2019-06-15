package com.keyob.payment.gateway.activities;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.fragment.ContactFragment;
import com.keyob.payment.gateway.fragment.HomeFragment;
import com.keyob.payment.gateway.fragment.QRCodePaymentFragment;
import com.keyob.payment.gateway.fragment.WalletManagementFragment;
import com.keyob.payment.gateway.helper.dataBase.DataSharedPrefrence;

import org.json.JSONException;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

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
                    ContactFragment contactFrament = new ContactFragment();
                    FragmentTransaction frag_contact_transaction = getSupportFragmentManager().beginTransaction();
                    frag_contact_transaction.replace(R.id.content, contactFrament, "contacts");
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
        DataSharedPrefrence sharedPrefrence =new DataSharedPrefrence(this);
        try {
            selectedWalletAtrr = sharedPrefrence.loadData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction frag_home_transaction = getSupportFragmentManager().beginTransaction();
        frag_home_transaction.replace(R.id.content, homeFragment, "homefragment");
        frag_home_transaction.commit();
    }


}
