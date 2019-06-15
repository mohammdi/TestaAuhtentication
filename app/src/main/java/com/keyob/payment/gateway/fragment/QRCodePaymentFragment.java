package com.keyob.payment.gateway.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keyob.payment.gateway.QrCodeViewPagerAdapter;
import com.keyob.payment.gateway.R;

public class QRCodePaymentFragment extends Fragment {

     private View view;
     private ViewPager viewPger;
     private TabLayout tableLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_qrcode_payment, container, false);
        viewPger = view.findViewById(R.id.viewPager_container);
        tableLayout = view.findViewById(R.id.tab);
        QrCodeViewPagerAdapter adapter = new QrCodeViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new ScanQRCode(),"scan QR Code");
        adapter.addFragment(new QRCode(),"Share QR Code");
        viewPger.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPger);
        return view;

    }

}
