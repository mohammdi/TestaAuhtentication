package com.keyob.payment.gateway.network;

import com.keyob.payment.gateway.model.*;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Mahmood_mohammadi on 11/6/2018.
 */

public interface RetrofitApiService {

    @GET("wallet/get")
    Call<List<Wallet>> getWalletList();

    @GET("wallet/getByUserId/{userId}")
    Call<List<HomeDto>> getWalletByUserId(@Path("userId") Long userId);

    @PUT(" ")
    Call<Wallet> updateWallet(@Body Wallet wallet);

    @POST(" ")
    Call<Wallet> createWallet(@Body Wallet wallet);

    @DELETE(" ")
    Call<Wallet> deleteWallet(@Body Wallet wallet);

    @Multipart
    @POST("/upload")
    Call<UploadImageResponse> uploadImage(@Part MultipartBody.Part file, @Part("name") RequestBody requestBody);


    @GET("wallet/getLogo/{id}")
    Call<UploadImageResponse> getWalletPic(@Path("id") Long id);

}
