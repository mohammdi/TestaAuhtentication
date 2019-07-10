package com.keyob.payment.gateway.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private  static Retrofit retrofit = null ;

    public static Retrofit getInstance(){

        if (retrofit==null){
            Gson gson = new GsonBuilder()
                    .enableComplexMapKeySerialization()
                    .serializeNulls()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .setPrettyPrinting().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(MyURLRepository.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }
}
