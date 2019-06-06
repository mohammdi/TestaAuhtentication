package com.keyob.payment.gateway.helper.convertors;

import com.keyob.payment.gateway.dal.dao.WalletDao;
import com.keyob.payment.gateway.dal.model.Wallet;
import com.keyob.payment.gateway.dal.model.WalletType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WalletConvertor {

    public List<Wallet>  walletHashMapToList (List<HashMap<String,String>> hashMapList){
       List<Wallet> walletList = new ArrayList<>();
        for (HashMap<String ,String> map :hashMapList)
            if (hashMapList.size()>0){
                Wallet wallet = new Wallet();
                WalletType walletType = new WalletType();
                wallet.setId(Long.valueOf(map.get(WalletDao.KEY_WALLET_ID)));
                wallet.setName(map.get(WalletDao.KEY_WALLET_NAME));
                walletType.setId(Long.valueOf(map.get(WalletDao.KEY_WALLET_WALLET_TYPE)));
                if (walletType.getId()==1L) {
                    walletType.setName("personal");
                }else{
                    walletType.setName("business");
                }
//                wallet.setWalletType((walletType));
                wallet.setUserId(Long.valueOf(map.get(WalletDao.KEY_WALLET_WALLET_USER_ID)));
                wallet.setSelected(Boolean.valueOf(map.get(WalletDao.KEY_WALLET_IS_SELECTED)));
                wallet.setDefault(Boolean.valueOf(map.get(WalletDao.Key_WALLET_IS_DEFAULT)));
                wallet.setAddress(map.get(WalletDao.KEY_WALLET_ADDRESS));
//                wallet.setBannerPath(map.get(WalletDao.KEY_WALLET_BANNER));
                /*wallet.setWalletAddress(mapWallets.get(WalletDao.KEY_WALLET_ADDRESS)); */   //  fix walletAddress And Address Field in dataBase
                wallet.setPassPayment(map.get(WalletDao.KEY_WALLET_PASS_PAYMENT));
                wallet.setPublicId(map.get(WalletDao.KEY_WALLET_PUBLIC_ID));
                wallet.setCreateDate(map.get(WalletDao.KEY_WALLET_CREATE_DATE));
//                wallet.setLogoPath(map.get(WalletDao.KEY_WALLET_LOGO));
                walletList.add(wallet);
            }
        return walletList;

    }
}
