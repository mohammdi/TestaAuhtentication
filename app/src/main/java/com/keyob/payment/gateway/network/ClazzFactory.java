package com.keyob.payment.gateway.network;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;

import com.google.gson.JsonParser;

import org.json.JSONException;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;


/**
 * Created by Mahmood_mohammadi on 3/28/2018.
 */

public class ClazzFactory {

    private Handler handler = new Handler();
    private Context context;
    private String dataRequet;


    public ClazzFactory(Context context) {
        this.context = context;
    }

    public void sender() {
        runnable.run();
    }

    public void stop() {
        handler.removeCallbacks(runnable);
    }


    private Runnable runnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {
            AsyncClazz sendRequest = new AsyncClazz(context);
            try {

                dataRequet = sendRequest.execute().get();

                if (dataRequet != "free" && dataRequet != "vpn") {

                    /**
                     *  Do change base Rate
                     */



                    /**
                     *  turn currency value
                     */

                    /**
                     *  save to shared Prefrence
                     */



                } else if (dataRequet.equals("free") || dataRequet.equals("vpn")) {

                    alertFactory(dataRequet);

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            handler.postDelayed(runnable, 3600000);

        }
    };

    public void alertFactory(String name) {
        if (name.equals("free")) {
            AlertDialog.Builder builderr = new AlertDialog.Builder(context);
            builderr.setTitle("هشدار");
            builderr.setMessage("اینترنت در دسترس نیست !!!");
            builderr.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builderr.create();
            alertDialog.show();

        } else {

            AlertDialog.Builder builderr = new AlertDialog.Builder(context);
            builderr.setTitle("هشدار");
            builderr.setMessage("شما قادر به دریافت نرخ های جدید نیستید.\n\n در صورت نیاز از vpn استفاده کنید !!!");
            builderr.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builderr.create();
            alertDialog.show();
        }
    }


    public String getDataRequet() {
        return dataRequet;
    }

    public void setDataRequet(String dataRequet) {
        this.dataRequet = dataRequet;
    }


}
