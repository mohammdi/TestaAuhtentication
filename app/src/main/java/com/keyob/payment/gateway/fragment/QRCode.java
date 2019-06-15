package com.keyob.payment.gateway.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.keyob.payment.gateway.R;


public class QRCode extends Fragment {

   private View view ;
   private ImageView  qrCode;
   private ImageView  qrCodeShare;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_qrcode, container, false);
        qrCode = view.findViewById(R.id.menu_bar_qr_code_image);
        qrCodeShare= view.findViewById(R.id.menu_bar_qr_code_share);

        return view;

    }
}
