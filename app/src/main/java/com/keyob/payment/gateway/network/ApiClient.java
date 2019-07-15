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


//    public static OkHttpClient getOAuthOkHttpClient(Context ctx) {
//        // Define the OkHttp Client with its cache!
//        // Assigning a CacheDirectory
//        File myCacheDir=new File(ctx.getCacheDir(),"OkHttpCache");
//        // You should create it...
//        int cacheSize=1024*1024;
//        Cache cacheDir=new Cache(myCacheDir,cacheSize);
//        Interceptor oAuthInterceptor=new OauthInterseptore();
//        HttpLoggingInterceptor httpLogInterceptor=new HttpLoggingInterceptor();
//        httpLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        return new OkHttpClient.Builder()
//                .cache(cacheDir)
//                .addInterceptor(oAuthInterceptor)
//                .addInterceptor(httpLogInterceptor)
//                .build();
//    }
}
