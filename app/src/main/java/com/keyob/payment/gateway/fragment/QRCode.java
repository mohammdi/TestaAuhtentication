package com.keyob.payment.gateway.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.PicassoImageDownloader;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.helper.URLAttacher;
import com.keyob.payment.gateway.network.MyURLRepository;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;
import java.net.URL;


public class QRCode extends Fragment {

   private View view ;
   private ImageView  qrCode;
   private TextView qrCodeShare;
   private TextView walletLink;
   private ImageView linkIconShare;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_qrcode, container, false);
        qrCode = view.findViewById(R.id.menu_bar_qr_code_image);
        qrCodeShare= view.findViewById(R.id.p_qr_code_share);
        walletLink = view.findViewById(R.id.p_qr_link);
        linkIconShare = view.findViewById(R.id.p_qr_link_share);

        final String url = URLAttacher.doAttach(MyURLRepository.GET_WALLET_QR_CODE, String.valueOf(SingletonWalletInfo.getInstance().getId()), null);
        PicassoImageDownloader.imageDownload(getContext(),SingletonWalletInfo.getInstance().getId(),SingletonWalletInfo.getInstance().getPublicId());
        Picasso.with(this.getContext()).load(url).into(qrCode);
        walletLink.setText(URLAttacher.doAttach(SingletonWalletInfo.getInstance().getBaseLink(),SingletonWalletInfo.getInstance().getWalletToken(),null));


        linkIconShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM,walletLink.getText().toString());
                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.sendTo_english)));
            }
        });

        qrCodeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM,url);
                shareIntent.setType("image/jpeg");
                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.sendTo_english)));
            }
        });
        return view;

    }
}
