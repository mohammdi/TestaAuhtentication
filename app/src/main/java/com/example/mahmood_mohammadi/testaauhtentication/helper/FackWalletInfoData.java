package com.example.mahmood_mohammadi.testaauhtentication.helper;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.mahmood_mohammadi.testaauhtentication.dal.l.model.Wallet;
import com.example.mahmood_mohammadi.testaauhtentication.R;

import java.util.ArrayList;
import java.util.List;
public class FackWalletInfoData {

    private List<Wallet> wallet_list = new ArrayList<>();

    public   List<Wallet> getData(Context context) {


        for (int i = 0; i <= 3; i++) {

            Wallet wallet= new Wallet();

            wallet.setName("school");
            wallet.setPublicId("KZ.546131354");
            wallet.setWalletAddress("keyob/m/mahmood");


            switch (i) {

                case 0:
                     {
                         wallet.setName("school");
//                        wallet.setLogoPath(ContextCompat.getDrawable(context,R.drawable.image_btn_shape));
//                        wallet.setBannerPath(ContextCompat.getDrawable(context,R.drawable.ic_delete));
                    }
                    break;
                case 1 :
                    wallet.setName("mahmood");
//                    wallet.setLogoPath(ContextCompat.getDrawable(context,R.drawable.image_profile_shape));
//                    wallet.setBannerPath(ContextCompat.getDrawable(context,R.drawable.ic_audiotrack_dark));
                    break;

                case 2 :
                    wallet.setName("working");
//                    wallet.setLogoPath(ContextCompat.getDrawable(context,R.drawable.sprinner_item_shape));
//                    wallet.setBannerPath(ContextCompat.getDrawable(context,R.drawable.ic_edite));
                    break;
                case 3 :

                    wallet.setName("kyoub");
//                    wallet.setLogoPath(ContextCompat.getDrawable(context,R.drawable.image_btn_shape));
//                    wallet.setBannerPath(ContextCompat.getDrawable(context,R.drawable.ic_mr_button_connected_00_dark));
                    break;
            }
           wallet_list.add(wallet);
        }

        return wallet_list ;
    }
}
