package com.keyob.payment.gateway.network.repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.model.Wallet;
import com.keyob.payment.gateway.network.ApiClient;
import com.keyob.payment.gateway.network.RetrofitApiService;
import com.keyob.payment.gateway.network.UploadImageResponse;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletRepositoryNetwork {
    private static WalletRepositoryNetwork instans;
    private MutableLiveData<List<Wallet>> allWallet;
    private MutableLiveData<List<HomeDto>> homeDtoWalletList;
    private MutableLiveData<Wallet> oneWallet;
    private RetrofitApiService apiService;
    public static Context context;

    public static WalletRepositoryNetwork getInstance(){
        if (instans==null){
            instans = new WalletRepositoryNetwork();
        }
        return instans;
    }

     WalletRepositoryNetwork() {
        apiService = ApiClient.getInstance().create(RetrofitApiService.class);
    }

    public MutableLiveData<List<Wallet>> getAllWallet(){
       allWallet= new MutableLiveData<>();
       apiService.getWalletList().enqueue(new Callback<List<Wallet>>() {
           @Override
           public void onResponse(Call<List<Wallet>> call, Response<List<Wallet>> response) {
               if (response.isSuccessful()){
                   allWallet.setValue(response.body());
               }

           }
           @Override
           public void onFailure(Call<List<Wallet>> call, Throwable t) {
               call.cancel();
           }
       });
        return allWallet;
    }

    public MutableLiveData<Wallet> updateWallet(Wallet wallet){
        oneWallet = new MutableLiveData<>();
        apiService.updateWallet(wallet).enqueue(new Callback<Wallet>() {
            @Override
            public void onResponse(Call<Wallet> call, Response<Wallet> response) {
                oneWallet.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Wallet> call, Throwable t) {
                oneWallet.setValue(null);
            }
        });

        return oneWallet;
    }

    // create wallet

    public MutableLiveData<Wallet> createWallet(Wallet wallet){
        oneWallet = new MutableLiveData<>();
        apiService.createWallet(wallet).enqueue(new Callback<Wallet>() {
            @Override
            public void onResponse(Call<Wallet> call, Response<Wallet> response) {
                oneWallet.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Wallet> call, Throwable t) {

            }
        });
        return oneWallet;
    }

    // find wallet by user Id
    public MutableLiveData<List<HomeDto>> findWalletByUserId(Long userId){
        homeDtoWalletList= new MutableLiveData<>();
        apiService.getWalletByUserId(userId).enqueue(new Callback<List<HomeDto>>() {
            @Override
            public void onResponse(Call<List<HomeDto>> call, Response<List<HomeDto>> response) {
                homeDtoWalletList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<HomeDto>> call, Throwable t) {
                call.cancel();
            }
        });

        return homeDtoWalletList;
    }


    public Void deleteWallet(Wallet wallet){
        apiService.deleteWallet(wallet).enqueue(new Callback<Wallet>() {
            @Override
            public void onResponse(Call<Wallet> call, Response<Wallet> response) {
                 if (response.isSuccessful()){
                     // todo
                 }
            }

            @Override
            public void onFailure(Call<Wallet> call, Throwable t) {
               call.cancel();
            }
        });
        return null;
    }


    // Map is used to multipart the file using okhttp3.RequestBody
    public  Integer upload (File file,String fileName) {
        File myfile = new File(file, fileName);
        final Integer[] responseStatus = new Integer[1];
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        Call<UploadImageResponse> call =apiService.uploadImage(fileToUpload, filename);
        call.enqueue(new Callback<UploadImageResponse>() {
            @Override
            public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                 if (response.isSuccessful());{
                    responseStatus[0] = response.code();
                }
            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {

            }
        });
        return responseStatus[0];

    }

}
