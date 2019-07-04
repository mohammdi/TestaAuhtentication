package com.keyob.payment.gateway.network.repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.keyob.payment.gateway.model.CreateRequestMoneyDto;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.model.PassBookRequestDto;
import com.keyob.payment.gateway.model.PassBookResponseDto;
import com.keyob.payment.gateway.model.QrCodeScanResponseDto;
import com.keyob.payment.gateway.model.RequestMoneyDto;
import com.keyob.payment.gateway.model.ResponseCorrelationDto;
import com.keyob.payment.gateway.model.Wallet;
import com.keyob.payment.gateway.network.ApiClient;
import com.keyob.payment.gateway.network.RetrofitApiService;
import com.keyob.payment.gateway.network.UploadImageResponse;

import java.io.File;
import java.util.List;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletRepositoryNetwork {
    private static WalletRepositoryNetwork instance;
    private MutableLiveData<List<Wallet>> allWalletMutable;
    private MutableLiveData<List<HomeDto>> homeDtoWalletList;
    private MutableLiveData<HomeDto> oneWalletMutable;
    private MutableLiveData<String> perPaymentResponseMutable;
    private MutableLiveData<QrCodeScanResponseDto> qrScanRespponseMutable;
    private MutableLiveData<ResponseBody> responseBody;
    private MutableLiveData<RequestMoneyDto> requestMoneyMutable;
    private MutableLiveData<List<PassBookResponseDto>> passBookResponseLiveData;
    private MutableLiveData<ResponseCorrelationDto> responseCorrelationMutable;
    private MutableLiveData<List<RequestMoneyDto>> requestMoneyMutableList;
    private MutableLiveData<List<RequestMoneyDto>> receiveMoneyRequestMutableList;
    private RetrofitApiService apiService;
    public static Context context;

    public static WalletRepositoryNetwork getInstance() {
        if (instance == null) {
            instance = new WalletRepositoryNetwork();
        }
        return instance;
    }

    WalletRepositoryNetwork() {
        apiService = ApiClient.getInstance().create(RetrofitApiService.class);
    }

    public MutableLiveData<List<Wallet>> getAllWallet() {
        allWalletMutable = new MutableLiveData<>();
        apiService.getWalletList().enqueue(new Callback<List<Wallet>>() {
            @Override
            public void onResponse(Call<List<Wallet>> call, Response<List<Wallet>> response) {
                if (response.isSuccessful()) {
                    allWalletMutable.setValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<Wallet>> call, Throwable t) {
                call.cancel();
            }
        });
        return allWalletMutable;
    }

    public MutableLiveData<List<HomeDto>> findWalletByUserId(Long userId) {
        homeDtoWalletList = new MutableLiveData<>();
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

    public MutableLiveData<HomeDto> findWalletByPublicId(String publicId) {
        oneWalletMutable = new MutableLiveData<>();
        apiService.getWalletByPublicId(publicId).enqueue(new Callback<HomeDto>() {
            @Override
            public void onResponse(Call<HomeDto> call, Response<HomeDto> response) {
                if (response.isSuccessful()) {
                    oneWalletMutable.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<HomeDto> call, Throwable t) {
                call.cancel();
            }
        });

        return oneWalletMutable;
    }

    public MutableLiveData<HomeDto> findWalletByPhoneNumber(String phoneNumber) {
        oneWalletMutable = new MutableLiveData<>();
        apiService.getWalletByPhoneNumber(phoneNumber).enqueue(new Callback<HomeDto>() {
            @Override
            public void onResponse(Call<HomeDto> call, Response<HomeDto> response) {
                if (response.isSuccessful()) {
                    oneWalletMutable.setValue(response.body());
                }else {
                    response.errorBody();
                    oneWalletMutable.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<HomeDto> call, Throwable t) {
                call.cancel();
            }
        });

        return oneWalletMutable;
    }

    public MutableLiveData<HomeDto> createWallet(HomeDto wallet) {
        oneWalletMutable = new MutableLiveData<>();
        apiService.createWallet(wallet.getName(),wallet.getPassPayment(),
                                wallet.getType(),wallet.getUserId(),
                                wallet.getAddress()).enqueue(new Callback<HomeDto>() {
            @Override
            public void onResponse(Call<HomeDto> call, Response<HomeDto> response) {
                if (response.isSuccessful()) {
                    oneWalletMutable.setValue(response.body());
                }else {
                    response.errorBody();
                    oneWalletMutable.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<HomeDto> call, Throwable t) {
                call.cancel();
            }
        });

        return oneWalletMutable;
    }

    public MutableLiveData<RequestMoneyDto> requestMoney(final CreateRequestMoneyDto requestMoney) {
        requestMoneyMutable = new MutableLiveData<>();
        apiService.requestMoney(requestMoney.getWalletId(), requestMoney.getPayerId(), requestMoney.getAmount(),
                requestMoney.getDescription(), requestMoney.getSenderId(), requestMoney.getSenderName(),
                requestMoney.getPayerWalletId(), requestMoney.getPayerName()).enqueue(new Callback<RequestMoneyDto>() {
            @Override
            public void onResponse(Call<RequestMoneyDto> call, Response<RequestMoneyDto> response) {
                if (response.isSuccessful()) {
                    requestMoneyMutable.setValue(response.body());
                } else {
                    response.errorBody();
                }

            }

            @Override
            public void onFailure(Call<RequestMoneyDto> call, Throwable t) {
                call.cancel();

            }
        });
        return requestMoneyMutable;
    }

    public MutableLiveData<String> payTheRequest(final RequestMoneyDto request, final long SenderWalletId) {
        perPaymentResponseMutable = new MutableLiveData<>();
        apiService.payTheRequest(SenderWalletId, request.getWalletId(), request.getAmount(), request.getId())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            perPaymentResponseMutable.setValue(response.body());
                        } else {

                            response.errorBody();
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        call.cancel();
                    }
                });

        return perPaymentResponseMutable;
    }

    public MutableLiveData<String> pTOpPayment(Long senderId, Long ReceiverId, Integer amount) {
        perPaymentResponseMutable = new MutableLiveData<>();
        apiService.pTOpPayment(senderId, ReceiverId, amount).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    perPaymentResponseMutable.setValue(response.body());
                } else {
                    response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.getMessage();
                t.getCause();
                call.cancel();
            }
        });

        return perPaymentResponseMutable;
    }

    public MutableLiveData<QrCodeScanResponseDto> getQToken(String token) {
        qrScanRespponseMutable = new MutableLiveData<>();
        apiService.getQToken(token).enqueue(new Callback<QrCodeScanResponseDto>() {
            @Override
            public void onResponse(Call<QrCodeScanResponseDto> call, Response<QrCodeScanResponseDto> response) {
                if (response.isSuccessful()) {
                    response.code();
                    qrScanRespponseMutable.setValue(response.body());
                } else {
                    response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<QrCodeScanResponseDto> call, Throwable t) {
                call.cancel();
            }
        });
        return qrScanRespponseMutable;
    }

    public MutableLiveData<ResponseCorrelationDto> reportRequsetPayment(String correlation) {
        responseCorrelationMutable = new MutableLiveData<>();
        apiService.getReportRequestPayment(correlation).enqueue(new Callback<ResponseCorrelationDto>() {
            @Override
            public void onResponse(Call<ResponseCorrelationDto> call, Response<ResponseCorrelationDto> response) {
                if (response.isSuccessful()) {
                    responseCorrelationMutable.setValue(response.body());
                } else {
                    response.errorBody();
                }

            }

            @Override
            public void onFailure(Call<ResponseCorrelationDto> call, Throwable t) {

            }
        });
        return responseCorrelationMutable;
    }

    public MutableLiveData<HomeDto> getWalletById(Long walletId) {
        oneWalletMutable = new MutableLiveData<>();
        apiService.getWalletById(walletId).enqueue(new Callback<HomeDto>() {
            @Override
            public void onResponse(Call<HomeDto> call, Response<HomeDto> response) {
                if (response.isSuccessful()) {
                    oneWalletMutable.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<HomeDto> call, Throwable t) {
                oneWalletMutable.setValue(null);
            }
        });

        return oneWalletMutable;
    }

    public MutableLiveData<RequestMoneyDto> getRequestById(UUID requestId) {
        requestMoneyMutable = new MutableLiveData<>();
        apiService.getRequestById(requestId).enqueue(new Callback<RequestMoneyDto>() {
            @Override
            public void onResponse(Call<RequestMoneyDto> call, Response<RequestMoneyDto> response) {

                if (response.isSuccessful()) {
                    requestMoneyMutable.setValue(response.body());
                } else {
                    response.errorBody();
                }

            }

            @Override
            public void onFailure(Call<RequestMoneyDto> call, Throwable t) {
                call.cancel();
            }
        });

        return requestMoneyMutable;
    }

    public synchronized MutableLiveData<List<RequestMoneyDto>> RequestMoneyGetByWalletId(Long walletId) {
        receiveMoneyRequestMutableList = new MutableLiveData<>();
        apiService.requestMoneyListByWalletId(walletId).enqueue(new Callback<List<RequestMoneyDto>>() {
            @Override
            public void onResponse(Call<List<RequestMoneyDto>> call, Response<List<RequestMoneyDto>> response) {
                if (response.isSuccessful()) {
                    receiveMoneyRequestMutableList.setValue(response.body());
                } else {
                    response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<List<RequestMoneyDto>> call, Throwable t) {
                call.cancel();
            }
        });

        return receiveMoneyRequestMutableList;
    }

    public synchronized MutableLiveData<List<RequestMoneyDto>> requestMoneyListByPeyerWalletId(Long walletId) {
        requestMoneyMutableList = new MutableLiveData<>();
        apiService.requestMoneyListByPeyerWalletId(walletId).enqueue(new Callback<List<RequestMoneyDto>>() {
            @Override
            public void onResponse(Call<List<RequestMoneyDto>> call, Response<List<RequestMoneyDto>> response) {
                if (response.isSuccessful()) {
                    requestMoneyMutableList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<RequestMoneyDto>> call, Throwable t) {
                call.cancel();
            }
        });

        return requestMoneyMutableList;
    }

    public MutableLiveData<List<PassBookResponseDto>> getPassBookList(PassBookRequestDto passBook) {
        passBookResponseLiveData = new MutableLiveData<>();
        apiService.getPassBook(passBook.getPage(),passBook.getWalletId(), passBook.getStartDate(),passBook.getEndDate(),
                                                passBook.getSearchType()).enqueue(new Callback<List<PassBookResponseDto>>() {
            @Override
            public void onResponse(Call<List<PassBookResponseDto>> call, Response<List<PassBookResponseDto>> response) {
                if (response.isSuccessful()){
                    response.code();
                    passBookResponseLiveData.setValue(response.body());
                }else {
                    response.errorBody();
                    passBookResponseLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<PassBookResponseDto>> call, Throwable t) {
                call.cancel();
                 passBookResponseLiveData.setValue(null);
            }
        });
        return passBookResponseLiveData;
    }
    public MutableLiveData<List<PassBookResponseDto>> getRecentPassBook(Long walletId) {
        passBookResponseLiveData = new MutableLiveData<>();
        apiService.getRecentPassBook(walletId).enqueue(new Callback<List<PassBookResponseDto>>() {
            @Override
            public void onResponse(Call<List<PassBookResponseDto>> call, Response<List<PassBookResponseDto>> response) {
                if (response.isSuccessful()){
                    response.code();
                    passBookResponseLiveData.setValue(response.body());
                }else {
                    response.errorBody();
                    passBookResponseLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<PassBookResponseDto>> call, Throwable t) {
                call.cancel();
                 passBookResponseLiveData.setValue(null);
            }
        });
        return passBookResponseLiveData;
    }


    // Map is used to multipart the file using okhttp3.RequestBody
    public Integer upload(File file, String fileName) {
        File myfile = new File(file, fileName);
        final Integer[] responseStatus = new Integer[1];
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        Call<UploadImageResponse> call = apiService.uploadImage(fileToUpload, filename);
        call.enqueue(new Callback<UploadImageResponse>() {
            @Override
            public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                if (response.isSuccessful()) ;
                {
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
