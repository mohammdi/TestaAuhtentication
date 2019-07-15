package com.keyob.payment.gateway.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.activities.HomeActivity;
import com.keyob.payment.gateway.helper.SingletonUserInfo;
import com.keyob.payment.gateway.helper.reCycelerViewHandler.ContactListRecyclerAdapter;
import com.keyob.payment.gateway.model.ContactDto;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment  implements  ContactListRecyclerAdapter.OnItemClickListener {


    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS =1;
    private RecyclerView recyclerView;
    private ContactListRecyclerAdapter adapter;
    private View view;
    private List<ContactDto> phoneContactList = new ArrayList<>();
    private List<ContactDto> myphoneContactList = new ArrayList<>();
    private WalletViewModelNetWork viewModel;
    private ContactListRecyclerAdapter recyclerAdapter;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        recyclerView = view.findViewById(R.id.contact_list_Recycler_View);
        progressBar = view.findViewById(R.id.contact_list_progressBar);

//        RequestPermission();


        getContactList(Long.valueOf(SingletonUserInfo.getInstance().getId()));
        setupToolbar();
        return view;

    }

//    private void RequestPermission() {
//        if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CONTACTS},1);
//        }else {
//
//            getAllContacts();
//        }
//
//    }

    public void setUpRecyclerView(List<ContactDto> dataList) {

        if (dataList.size() > 0) {

            recyclerAdapter = new ContactListRecyclerAdapter(getContext(), dataList);
            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerAdapter.setOnItemClickListener(ContactFragment.this);
            recyclerView.setAdapter(recyclerAdapter);
            progressBar.setVisibility(view.GONE);
        } else {
            Toast.makeText(getContext(), "nothing wallet in your account", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(view.GONE);

        }

    }

    private void setupToolbar() {
        Toolbar toolbar = view.findViewById(R.id.contact_list_toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HomeActivity.class));
                getActivity().finish();
            }
        });
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setElevation(10);
        actionBar.setBackgroundDrawable(getActivity().getDrawable(R.drawable.wallet_item_gradient_selector));

    }

    private void getContactList(Long userId) {
        progressBar.setVisibility(View.VISIBLE);
        viewModel = ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
        viewModel.getContactByUserId(userId).observe(this, new Observer<List<ContactDto>>() {
            @Override
            public void onChanged(@Nullable List<ContactDto> contactList) {
                phoneContactList = contactList;
                setUpRecyclerView(phoneContactList);
            }
        });
    }



    private void getAllContacts() {
        ContactDto cnt = null ;
        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                cnt = new ContactDto();
                String name = "";
                String number= "";
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
        }
    }


    @Override
    public void onclick(int position) {

        Toast.makeText(getContext(), "test listener info ", Toast.LENGTH_SHORT).show();
    }
}
