package com.keyob.payment.gateway.helper.dataBase;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.model.Wallet;

import java.util.ArrayList;
import java.util.List;
public class FackWalletInfoData {

    private List<Wallet> wallet_list = new ArrayList<>();

    public   List<Wallet> getData(Context context) {


        for (int i = 0; i <= 4; i++) {

            Wallet wallet= new Wallet();

            switch (i) {

                case 0:
                     {
                         wallet.setName("school");
//                         wallet.setPublicId("KZ.546131354");
//                        wallet.setLogoPath(ContextCompat.getDrawable(context,R.drawable.icon_google));
//                        wallet.setBannerPath(ContextCompat.getDrawable(context,R.drawable.ic_delete));
                    }
                    break;
                case 1 :
                    wallet.setName("mahmood");
//                    wallet.setPublicId("KZ.546131354");
//                    wallet.setLogoPath(ContextCompat.getDrawable(context,R.drawable.icon_google));
//                    wallet.setBannerPath(ContextCompat.getDrawable(context,R.drawable.ic_audiotrack_dark));
                    break;

                case 2 :
                    wallet.setName("working");
//                    wallet.setPublicId("KZ.546131354");
//                    wallet.setLogoPath(ContextCompat.getDrawable(context,R.drawable.icon));
//                    wallet.setBannerPath(ContextCompat.getDrawable(context,R.drawable.ic_link_management));
                    break;

                case 3 :
                    wallet.setName("working");
//                    wallet.setPublicId("KZ.546131354");

//                    wallet.setLogoPath(ContextCompat.getDrawable(context,R.drawable.icon));
//                    wallet.setBannerPath(ContextCompat.getDrawable(context,R.drawable.ic_link_management));
                    break;

                case 4:
                    wallet.setName("mahmood");
//                    wallet.setPublicId("KZ.546131354");

//                    wallet.setLogoPath(ContextCompat.getDrawable(context,R.drawable.icon_google));
//                    wallet.setBannerPath(ContextCompat.getDrawable(context,R.drawable.ic_audiotrack_dark));
                    break;

            }
           wallet_list.add(wallet);
        }

        return wallet_list ;
    }
}
