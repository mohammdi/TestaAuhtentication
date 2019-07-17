package com.keyob.payment.gateway.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OauthUserApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit geUserInfoInstance(final String authorizationValue) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
                                                      @Override
                                                      public Response intercept(Chain chain) throws IOException {
                                                          Request original = chain.request();

                                                          Request request = original.newBuilder()
                                                                  .header("Authorization", authorizationValue)
                                                                  .method(original.method(), original.body())
                                                                  .build();

                                                          return chain.proceed(request);
                                                      }
                                                  });
        OkHttpClient client = httpClient.build();


        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .enableComplexMapKeySerialization()
                    .serializeNulls()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .setPrettyPrinting().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://account.keyob.com/identity/connect/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }

        return retrofit;

    }
}
