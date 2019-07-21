package com.keyob.payment.gateway.fragment;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.activities.ProfileActivity;
import com.keyob.payment.gateway.activities.RequestMoneyContainerActivity;
import com.keyob.payment.gateway.activities.SelectDatePassBookActivity;
import com.keyob.payment.gateway.activities.TagListActivity;
import com.keyob.payment.gateway.helper.AlertFactory;
import com.keyob.payment.gateway.helper.SingletonUserInfo;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.helper.dataBase.DataSharedPrefrence;
import com.keyob.payment.gateway.helper.transform.PrettyShow;
import com.keyob.payment.gateway.model.ContactDto;
import com.keyob.payment.gateway.model.ContactLessDto;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.model.SubmitContactDto;
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


    private ImageView home_requestMoney;
    private ImageView home_passbook;
    private ImageView home_tag;
    private ImageView menuBar;
    private ImageView notification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        profileHome = view.findViewById(R.id.home_wallet_pic);
        walletNameHome = view.findViewById(R.id.home_walletName);
        balanceHome = view.findViewById(R.id.home_balance);
        home_passbook = view.findViewById(R.id.home_pass_Book);
        home_requestMoney= view.findViewById(R.id.home_request_money);
        home_tag = view.findViewById(R.id.home_tag_management);
        menuBar = view.findViewById(R.id.home_drawer);
        notification = view.findViewById(R.id.home_notifation);

        sharedPreferences = getContext().getSharedPreferences("contact Request", getActivity().getApplicationContext().MODE_PRIVATE);
        if (!sharedPreferences.getBoolean(FIRST_LOGIN, false)) {
            checkContactPermission();
        }

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


        home_requestMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RequestMoneyContainerActivity.class);
                 startActivity(intent);
            }
        });

        home_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getActivity(), TagListActivity.class);
               startActivity(intent);
            }
        });

        home_passbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getActivity(), SelectDatePassBookActivity.class);
               startActivity(intent);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), ProfileActivity.class);
//                startActivity(i);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    getActivity().overridePendingTransition(R.anim.slid_in_right, R.anim.slid_out_right);
//                }
//
            }
        });

        menuBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ProfileActivity.class);
                startActivity(i);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getActivity().overridePendingTransition(R.anim.slid_in_right, R.anim.slid_out_right);
                }
            }
        });

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
