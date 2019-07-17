package com.keyob.payment.gateway.fragment;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.keyob.payment.gateway.activities.HomeActivity;
import com.keyob.payment.gateway.activities.LoginActivity;
import com.keyob.payment.gateway.activities.RequestMoneyContainerActivity;
import com.keyob.payment.gateway.activities.SelectDatePassBookActivity;
import com.keyob.payment.gateway.activities.SplashScreenActivity;
import com.keyob.payment.gateway.activities.TagListActivity;
import com.keyob.payment.gateway.helper.SingletonUserInfo;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.helper.dataBase.DataSharedPrefrence;
import com.keyob.payment.gateway.helper.transform.PrettyShow;
import com.keyob.payment.gateway.model.ContactDto;
import com.keyob.payment.gateway.model.ContactLessDto;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.model.SubmitContactDto;
import com.keyob.payment.gateway.helper.AlertFactory;
import com.keyob.payment.gateway.network.ApiClient;
import com.keyob.payment.gateway.network.InternetStatus;
import com.keyob.payment.gateway.network.MyURLRepository;
import com.keyob.payment.gateway.network.RetrofitApiService;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    public static final String FIRST_LOGIN = "first login";
    public static final int PERMISSION_REQUEST_CONTACT = 79;
    private View view;
    private List<HomeDto> walletList;
    private GridLayout gridLayout;
    private ActionBarDrawerToggle actionBarToggle;
    private WalletViewModelNetWork walletViewModelNetwork;
    private CircularImageView profileHome;
    private TextView balanceHome;
    private TextView walletNameHome;
    private SingletonWalletInfo instance;
    private DataSharedPrefrence prefrence;
    private SharedPreferences sharedPreferences;
    private RetrofitApiService apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        gridLayout = view.findViewById(R.id.home_grid_layout);
        profileHome = view.findViewById(R.id.home_wallet_pic);
        walletNameHome = view.findViewById(R.id.home_walletName);
        balanceHome = view.findViewById(R.id.home_balance);
        sharedPreferences = getContext().getSharedPreferences("contact Request", getActivity().getApplicationContext().MODE_PRIVATE);
        if (!sharedPreferences.getBoolean(FIRST_LOGIN, false)) {

            checkContactPermission();
        }
        setSingleEvent(gridLayout);
        setUpToolBar();
        // check internet status
        InternetStatus internetStatus = new InternetStatus(getContext());
        Boolean hasInternet = internetStatus.statusNetWOrk();

        instance = SingletonWalletInfo.getInstance();

        if (!hasInternet) {
            AlertFactory alertFactory = new AlertFactory(getContext());
            alertFactory.singleButtonAlert("هشدار", "عدم دسترسی به شبکه!");
        } else if (instance.getId() != null) {
            requestWalletByWalletId(walletViewModelNetwork, instance.getId());
        } else {
            requestGetWalletByUserId(walletViewModelNetwork);
        }


//            }
        return view;
    }

    private void checkContactPermission() {
        RequestPermission();

    }

    private void RequestPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_CONTACT);
        }

    }

    private void getAllContacts() {
        List<ContactDto> phoneContactList = new ArrayList<>();
        ContactDto cnt = null;
        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                cnt = new ContactDto();
                String name = "";
                String number = "";
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    cnt.setName(name);
                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id},
                            null);
                    if (phoneCursor != null) {
                        if (phoneCursor.moveToNext()) {
                            number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            cnt.setMobileNumber(number);
                            //At here You can add phoneNUmber and Name to you listView ,ModelClass,Recyclerview
                            phoneCursor.close();
                        }


                    }
                }

                phoneContactList.add(cnt);
            }
            SubmitContactDto dto = new SubmitContactDto();
            dto.setOwnerMobileNumber(SingletonUserInfo.getInstance().getMobileNumber());
            List<ContactLessDto> MuCustomMb = new ArrayList<>();
            for (ContactDto c : phoneContactList) {
                if (c.getMobileNumber() != null) {
                    switch (c.getMobileNumber()) {

                        case "0935 495 6762":
                            ContactLessDto contact = new ContactLessDto();
                            contact.setMobileNumber(c.getMobileNumber());
                            contact.setName(c.getName());
                            MuCustomMb.add(contact);
                            break;
                        case "0935 652 5277":
                            ContactLessDto contact1 = new ContactLessDto();
                            contact1.setMobileNumber(c.getMobileNumber());
                            contact1.setName(c.getName());
                            MuCustomMb.add(contact1);
                            break;
                        case "+989101917006":
                            ContactLessDto contact2 = new ContactLessDto();
                            contact2.setMobileNumber(c.getMobileNumber());
                            contact2.setName(c.getName());
                            MuCustomMb.add(contact2);
                            break;
                    }

                }

            }
            dto.setContacts(MuCustomMb);
            apiService = ApiClient.getInstance().create(RetrofitApiService.class);
            apiService.submitContact(dto).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    if (response.isSuccessful()) {
                        Log.i("CONTACT_SUBMIT", "contacts are submit on server ");
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getContext(), "submit number Canceled", Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });
        }

    }


    private void requestWalletByWalletId(WalletViewModelNetWork viewModel, Long walletId) {

        viewModel = ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
        viewModel.getWalletById(walletId).observe(this, new Observer<HomeDto>() {
            @Override
            public void onChanged(@Nullable HomeDto homeDto) {
                if (homeDto != null) {
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


    private void requestWalletImage(String url, Long walletId) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append(walletId);

        Picasso.with(getContext())
                .load(sb.toString())
                .into(profileHome);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CONTACT) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getAllContacts();
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
            } else {
                new AlertDialog.Builder(getContext()).setTitle("مجوز دسترسی به مخاطبین").
                        setMessage("شما مجوز دسترسی به مخاطبین را رد کرده اید. این سرویس برای شما غیر فعال است").show();
            }
        } else {

        }
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

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBarToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, 0, 0);
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
                    case R.id.nav_logout:
                        AlertDialog.Builder builderr = new AlertDialog.Builder(getContext());
                        builderr.setTitle("خروج");
                        builderr.setIcon(R.drawable.icon);
                        builderr.setMessage("شما میخواهید خارج شوید.ایا مطمئن هستید ؟");
                        builderr.setPositiveButton(R.string.yes_persian, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finishAffinity();
                                System.exit(0);

                            }
                        });
                        builderr.setNegativeButton(R.string.no_persian, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                            }
                        });

                        builderr.show();
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
                            intent = new Intent(getActivity(), TagListActivity.class);
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
        if (profileHome.getDrawable() == null) {
            profileHome.setImageDrawable(getResources().getDrawable(R.drawable.ic_profile));
        }
    }

    // daclare user information in drawer navigation
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SharedPreferences.Editor sharedPreferencesEditor =
                getActivity().getApplicationContext().getSharedPreferences("contact Request", getActivity().getApplicationContext().MODE_PRIVATE).edit();
        sharedPreferencesEditor.putBoolean(
                FIRST_LOGIN, true);
        sharedPreferencesEditor.apply();

    }
}
