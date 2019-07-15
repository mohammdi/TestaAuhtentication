package com.keyob.payment.gateway.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class OauthInterseptore implements Interceptor {

    private static final String TAG = "OAuthInterceptor";
    private String accessToken, accessTokenType;

    @Override
    public Response intercept(Chain chain) throws IOException {

        //find the token
//        OauthToken oauthToken=new OauthToken();
//        accessToken=oauthToken.getAccessToken();
//        accessTokenType=oauthToken.getTokenType();
//        //add it to the request
//        Request.Builder builder = chain.request().newBuilder();
//        if (!TextUtils.isEmpty(accessToken)&&!TextUtils.isEmpty(accessTokenType)) {
//            Log.e(TAG,"In the interceptor adding the header authorization with : "+accessTokenType+" " + accessToken);
//            builder.header("Authorization",accessTokenType+" " + accessToken);
//        }else{
//            Log.e(TAG,"In the interceptor there is a fuck with : "+accessTokenType+" " + accessToken);
//            //you should launch the loginActivity to fix that:
//            Intent i = new Intent(MyApplication.instance, MainActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//            MyApplication.instance.startActivity(i);
//        }
//        //proceed to the call
//        return chain.proceed(builder.build());

        return null;

    }

}
