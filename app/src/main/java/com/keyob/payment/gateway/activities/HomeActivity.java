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
import com.keyob.payment.gateway.fragment.WalletManagmentFragment;
import com.keyob.payment.gateway.helper.DataBase.DataSharedPrefrence;

import org.json.JSONException;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PHOTOS = "photos";
    private static final String TAG_MOVIES = "movies";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;
    HashMap<String, String> selectedWalletAtrr = new HashMap<>();


    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_wallet_list:
                    WalletManagmentFragment walletManagementFragment = new WalletManagmentFragment();
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

//                case R.id.navigation_add_money:
//                    AddMoneyFragment addMoneyFragment = new AddMoneyFragment();
//                    FragmentTransaction frag_addMoney_Transaction = getSupportFragmentManager().beginTransaction();
//                    frag_addMoney_Transaction.replace(R.id.content, addMoneyFragment, "addMoney");
//                    frag_addMoney_Transaction.commit();
//                    return true;

                case R.id.navigation_contact:
                    ContactFragment contactFrament = new ContactFragment();
                    FragmentTransaction frag_contact_transaction = getSupportFragmentManager().beginTransaction();
                    frag_contact_transaction.replace(R.id.content, contactFrament, "contacts");
                    frag_contact_transaction.commit();
                    return true;

//                case R.id.navigation_linkManagement:
//                    LinkManagmentFragment linkFragment = new LinkManagmentFragment();
//                    FragmentTransaction frag_link_transaction = getSupportFragmentManager().beginTransaction();
//                    frag_link_transaction.replace(R.id.content, linkFragment, "link Managment");
//                    frag_link_transaction.commit();
//                    return true;

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
        setUpViwe();
    }



    private void setUpViwe() {
//        setUpToolBar();
    }


   /* private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout =(DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        ActionBarDrawerToggle actionBarToggle = new ActionBarDrawerToggle(this ,
                drawerLayout,toolbar,0,0);
        drawerLayout.addDrawerListener(actionBarToggle);
        actionBarToggle.syncState();
    }
*/


}
