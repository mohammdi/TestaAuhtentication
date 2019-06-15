package com.keyob.payment.gateway.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.keyob.payment.gateway.model.*;
import com.keyob.payment.gateway.network.repository.WalletRepositoryNetwork;

import java.util.List;

public class WalletViewModelNetWork extends AndroidViewModel {
    private LiveData<List<Wallet>> allWallet ;
    private WalletRepositoryNetwork repositoryNetWork;

    public WalletViewModelNetWork(@NonNull Application application) {
        super(application);
        if (allWallet!=null){
            return;
        }
        repositoryNetWork = WalletRepositoryNetwork.getInstance();
//        allWallet = repositoryNetWork.getAllWallet();
    }

    public LiveData<List<Wallet>> getAllWallet(){
            return repositoryNetWork.getAllWallet();
    }

    public LiveData<List<HomeDto>> getWalletByUserId(Long userId){
        return  repositoryNetWork.findWalletByUserId(userId);
    }

    public LiveData<Wallet> updateWallet(Wallet wallet){
         return  repositoryNetWork.updateWallet(wallet);
    }
}
