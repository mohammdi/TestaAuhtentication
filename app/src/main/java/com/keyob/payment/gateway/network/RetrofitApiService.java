package com.keyob.payment.gateway.network;

import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.model.QrCodeScanResponseDto;
import com.keyob.payment.gateway.model.RequestMoneyDto;
import com.keyob.payment.gateway.model.ResponseCorrelationDto;
import com.keyob.payment.gateway.model.Wallet;

import java.util.List;
import java.util.UUID;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Mahmood_mohammadi on 11/6/2018.
 */

public interface RetrofitApiService {


    // ******************** WALLET ********************

    @GET("wallet/get")
    Call<List<Wallet>> getWalletList();


    @GET("wallet/getByUserId/{userId}")
    Call<List<HomeDto>> getWalletByUserId(@Path("userId") Long userId);


    @GET("wallet/get/{id}")
    Call<HomeDto> getWalletById(@Path("id") Long id);


    @GET("wallet/getByPublicId/{PublicId}")
    Call<HomeDto> getWalletByPublicId(@Path("PublicId") String PublicId);


    @GET("wallet/getByPhoneNumber/{phoneNumber}")
    Call<HomeDto> getWalletByPhoneNumber(@Path("phoneNumber") String phoneNumber);



    // ******************** REQUEST ********************


    @GET("request/get/{id}")
    Call<RequestMoneyDto> getRequestById(@Path("id") UUID id);

    @GET("request/getByWalletId/{id}")
    Call<List<RequestMoneyDto>> requestMoneyListByWalletId(@Path("id") Long id);


    @GET("request/getByPayerId/{PayerWalletId}")
    Call<List<RequestMoneyDto>> requestMoneyListByPeyerWalletId(@Path("PayerWalletId") Long PayerWalletId);

    @FormUrlEncoded
    @POST("request/post")
    Call<RequestMoneyDto> requestMoney(@Field("WalletId")Long WalletId,
                                       @Field("PayerId") String PayerId,
                                       @Field("Amount")Integer Amount,
                                       @Field("Description")String Description,
                                       @Field("SenderId") String SenderId,
                                       @Field("SenderName") String SenderName,
                                       @Field("PayerWalletId") Long PayerWalletId,
                                       @Field("PayerName") String PayerName);
    @DELETE("request/delete/{id}")
    Call<Void> deleteRequestBySender(@Path("id") UUID id);

    @DELETE("request/deleteByPayer/{id}")
    Call<Void> deleteRequestByPayer(@Path("id") UUID id);




    // *************** PAYMENT ***************


    @FormUrlEncoded
    @POST("payment/payRequest")
    Call<String> payTheRequest(@Field("SenderId") Long SenderId,
                                                 @Field("ReceiverId") Long ReceiverId,
                                                 @Field("Amount")Integer Amount,
                                                 @Field("RequestId") UUID RequestId);
    @FormUrlEncoded
    @POST("Payment/doPayment")
    Call<String> pTOpPayment(@Field("SenderId") Long SenderId,
                             @Field("ReceiverId") Long ReceiverId,
                             @Field("Amount")Integer Amount);

    @GET("payment/report/{correlationId}")
    Call<ResponseCorrelationDto> getReportRequestPayment(@Path("correlationId") String PayerWalletId);




    // ***************  QR CODE  ***************

    @GET("qr/getQToken/{id}")
    Call<QrCodeScanResponseDto> getQToken(@Path("id") String id);


    @Multipart
    @POST("/upload")
    Call<UploadImageResponse> uploadImage(@Part MultipartBody.Part file, @Part("name") RequestBody requestBody);


}
