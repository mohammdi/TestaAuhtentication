package com.keyob.payment.gateway.network;

import com.keyob.payment.gateway.helper.enums.WalletType;
import com.keyob.payment.gateway.model.ContactDto;
import com.keyob.payment.gateway.model.ContactLessDto;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.model.PassBookRequestDto;
import com.keyob.payment.gateway.model.PassBookResponseDto;
import com.keyob.payment.gateway.model.QrCodeScanResponseDto;
import com.keyob.payment.gateway.model.RequestMoneyDto;
import com.keyob.payment.gateway.model.ResponseCorrelationDto;
import com.keyob.payment.gateway.model.SubmitContactDto;
import com.keyob.payment.gateway.model.TagDto;
import com.keyob.payment.gateway.model.Users;
import com.keyob.payment.gateway.model.Wallet;
import com.keyob.payment.gateway.network.dto.OauthToken;
import com.keyob.payment.gateway.network.dto.OauthUserInfo;

import java.util.Date;
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
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @FormUrlEncoded
    @POST("wallet/post")
    Call<HomeDto> createWallet(@Field("Name") String Name,
                               @Field("PassPayment") String PassPayment,
                               @Field("Type") Integer Type,
                               @Field("UserId") Long UserId,
                               @Field("Address") String Address);


    @DELETE("wallet/delete/{id}")
    Call<Void> deleteWallet(@Path("id") Long id);


    @Headers({ "Content-Type: text/json; charset=utf-8","x-ms-logging-context: fixtures.bodyformdata.Formdatas uploadFileViaBody"})
    @PUT("wallet/put/{Id}")
    Call<HomeDto> updateWallet(@Path("Id") Long Id,@Body HomeDto model);


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
    @POST("payment/payTag")
    Call<String> payTheTag(@Field("SenderId") Long SenderId,
                               @Field("ReceiverId") Long ReceiverId,
                               @Field("Amount")Integer Amount,
                               @Field("TagId") UUID TagId);
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


    //**************** passBook ******************

    @FormUrlEncoded
    @POST("wallet/search")
    Call<List<PassBookResponseDto>> getPassBook( @Field("Page") Integer Page,
                                                 @Field("WalletId")Long WalletId,
                                                 @Field("StartDate") String StartDate,
                                                 @Field("EndDate") String EndDate,
                                                 @Field("SearchType") Integer SearchType);


    @GET("wallet/getRecentPassBook/{walletId}")
    Call<List<PassBookResponseDto>> getRecentPassBook(@Path("walletId") Long walletId);



    //**************** TAG ******************

    @GET("tag/get/{id}")
    Call<TagDto> getTagById(@Path("id") UUID id);

    @GET("Tag/getByWalletId/{id}")
    Call<List<TagDto>> getTagByWalletId(@Path("id") Long id );

    @FormUrlEncoded
    @POST("tag/post")
    Call<TagDto> createTag(@Field("Subject") String Subject,@Field("Description") String Description,
                           @Field("Price")  Integer Price,@Field("PriceByPayer")  Boolean PriceByPayer,
                           @Field("Count")  Integer Count,@Field("HasInfinitCount")  Boolean HasInfinitCount,
                           @Field("WalletId")  Long  WalletId);


    @Headers({ "Content-Type: text/json; charset=utf-8","x-ms-logging-context: fixtures.bodyformdata.Formdatas uploadFileViaBody"})
    @PUT("tag/put/{Id}")
    Call<TagDto> updateTag(@Path("Id") UUID Id ,@Body TagDto model);


    @DELETE("tag/delete/{id}")
    Call<Void> deleteTag(@Path("id") UUID id);


    //**************** register ******************


    @POST("account/registerMobileNumber")
    Call<String> registerPhoneNumber(@Body String phoneNumber);


    @FormUrlEncoded
    @POST("account/register")
    Call<Users> registerUser(@Field("Password") String Password,@Field("FirstName") String FirstName,
                             @Field("MobileNumber") String MobileNumber,@Field("LastName") String LastName,
                             @Field("Email") String Email);

    //****************  contact  ******************

    @GET("contact/getByUserId/{UserId}")
    Call<List<ContactDto>>getContactByUserId(@Path("UserId") Long UserId);



    @POST("contact/post")
    Call<Void>  submitContact (@Body SubmitContactDto model);


    //****************  LOGIN  ******************

    @POST("userinfo")
    Call<OauthUserInfo> getUserInfo();

    @FormUrlEncoded
    @POST("token")
    Call<OauthToken> getAuthorizationToken (@Field("grant_type") String grant_type,
                                            @Field("scope") String scope,
                                            @Field("username") String username,
                                            @Field("password") String password );


    @Multipart
    @POST("/upload")
    Call<UploadImageResponse> uploadImage(@Part MultipartBody.Part file, @Part("name") RequestBody requestBody);




}
