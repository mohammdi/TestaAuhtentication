package com.keyob.payment.gateway.helper.requestHelper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.keyob.payment.gateway.helper.restClient.ApiService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestSender {

    private JSONObject jsonObject;
    private ApiService apiService;
    private  Context context;
    private AppCompatActivity destinationActivity;

    public RequestSender() {
    }

    public RequestSender(ApiService apiService, Context context, AppCompatActivity destinationActivity) {
        this.apiService = apiService;
        this.context = context;
        this.destinationActivity = destinationActivity;
    }

    private void sendReq(String key, String data) {
        Map<String, String> params = new HashMap<String, String>();

        params.put(key,data);
        jsonObject = new JSONObject(params);
        apiService.verificationConfCode(new ApiService.OnResponsReceive() {
            @Override
            public void receive(String message) {
                if (message != null) {

                    Intent intent =new Intent(context,destinationActivity.getClass());
                    context.startActivity(intent);
                    destinationActivity.finish();

                } else {

                    Toast.makeText(context, "your email incorrect\n please try again", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onError() {
                Toast.makeText(context, "some error from server!!! \n please try again", Toast.LENGTH_LONG).show();
            }
        }, jsonObject);

    }
}


