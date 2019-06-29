package com.keyob.payment.gateway.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.PicassoImageDownloader;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.helper.URLAttacher;
import com.keyob.payment.gateway.network.MyURLRepository;
import com.squareup.picasso.Picasso;

import java.io.File;


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

        String url = URLAttacher.doAttach(MyURLRepository.GET_WALLET_QR_CODE, String.valueOf(SingletonWalletInfo.getInstance().getId()), null);
        PicassoImageDownloader.imageDownload(getContext(),SingletonWalletInfo.getInstance().getId(),SingletonWalletInfo.getInstance().getPublicId());
        Picasso.with(this.getContext()).load(url).into(qrCode);

        qrCodeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File imageFile = new File(PicassoImageDownloader.getFileName(SingletonWalletInfo.getInstance().getPublicId()));
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM,imageFile);
                shareIntent.setType("image/jpeg");
                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.sendTo_english)));

            }
        });
        return view;

    }
}
