package com.keyob.payment.gateway.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.keyob.payment.gateway.model.*;
import com.keyob.payment.gateway.network.repository.WalletRepositoryNetwork;

import java.util.List;
import java.util.UUID;

public class WalletViewModelNetWork extends AndroidViewModel {
    private LiveData<List<Wallet>> allWallet;
    private WalletRepositoryNetwork repositoryNetWork;

    public WalletViewModelNetWork(@NonNull Application application) {
        super(application);
        if (allWallet != null) {
            return;
        }
        repositoryNetWork = WalletRepositoryNetwork.getInstance();
    }

    public LiveData<List<Wallet>> getAllWallet() {
        return repositoryNetWork.getAllWallet();
    }

    public LiveData<List<HomeDto>> getWalletByUserId(Long userId) {
        return repositoryNetWork.findWalletByUserId(userId);
    }

    public LiveData<HomeDto> findWalletByPublicId(String publicId) {
        return repositoryNetWork.findWalletByPublicId(publicId);
    }

    public LiveData<HomeDto> findWalletByPhoneNumber(String phoneNumber) {
        return repositoryNetWork.findWalletByPhoneNumber(phoneNumber);
    }

    public LiveData<HomeDto> createWallet(HomeDto wallet) {
        return repositoryNetWork.createWallet(wallet);
    }

    public LiveData<RequestMoneyDto> requestMoney(CreateRequestMoneyDto requestMoney) {
        return repositoryNetWork.requestMoney(requestMoney);
    }

    public LiveData<HomeDto> getWalletById(Long walletId) {
        return repositoryNetWork.getWalletById(walletId);
    }

    public LiveData<RequestMoneyDto> getRequestById(UUID requestId) {
        return repositoryNetWork.getRequestById(requestId);
    }

    public LiveData<List<RequestMoneyDto>> RequestMoneyGetByWalletId(Long walletId) {
        return repositoryNetWork.RequestMoneyGetByWalletId(walletId);
    }

    public LiveData<List<RequestMoneyDto>> requestMoneyListByPeyerWalletId(Long walletId) {
        return repositoryNetWork.requestMoneyListByPayerWalletId(walletId);
    }

    public LiveData<String> payTheRequest(RequestMoneyDto request, Long SenderWalletId) {
        return repositoryNetWork.payTheRequest(request, SenderWalletId);
    }

    public LiveData<String> payTag(TagDto tag, Long SenderWalletId) {
        return repositoryNetWork.payTag(tag, SenderWalletId);
    }

    public LiveData<String> pTOpPayment(Long senderId, Long receiverId, Integer amount) {
        return repositoryNetWork.pTOpPayment(senderId, receiverId, amount);
    }

    public LiveData<ResponseCorrelationDto> reportRequsetPayment(String request) {
        return repositoryNetWork.reportRequsetPayment(request);
    }

    public LiveData<QrCodeScanResponseDto> getQToken(String token) {
        return repositoryNetWork.getQToken(token);
    }

    public LiveData<List<PassBookResponseDto>> getPassBookList(PassBookRequestDto passBookRequest) {
        return repositoryNetWork.getPassBookList(passBookRequest);
    }

    public LiveData<List<PassBookResponseDto>> getRecentPassBook(Long walletId) {
        return repositoryNetWork.getRecentPassBook(walletId);
    }

    public LiveData<TagDto> getTagById(UUID id) {
        return repositoryNetWork.getTagById(id);
    }

    public LiveData<List<TagDto>> getTagByWalletId(Long id) {
        return repositoryNetWork.getTagByWalletId(id);
    }

    public LiveData<TagDto> createTag(TagDto tag, Long walletId) {
        return repositoryNetWork.createTag(tag, walletId);
    }

    public LiveData<TagDto> updateTag(TagDto tag) {
        return repositoryNetWork.updateTag(tag);
    }

}
