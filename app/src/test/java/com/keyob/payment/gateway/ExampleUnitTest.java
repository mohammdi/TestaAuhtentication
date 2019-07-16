package com.keyob.payment.gateway;

import android.util.Base64;

import com.google.gson.Gson;
import com.keyob.payment.gateway.helper.CustomPersianCalendar;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.network.ApiClient;
import com.keyob.payment.gateway.network.RetrofitApiService;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        String dtStart = "Sat Jun 22 13:25:34 GMT+04:30 2019";
        String str ="2019-06-29T12:06:01.833";
        Date date = new Date();
        System.out.println(date);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Locale locale = new Locale("USA");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-DD HH:MM:SS", locale);
//        Date date = null;
//        date = new date.(dtStart);
        String persianDate = CustomPersianCalendar.getPersianDate(date);
        int persianMonth = CustomPersianCalendar.getPersianMonth(date);
        String persianDate1 = CustomPersianCalendar.getPersianDate(date);
        System.out.println("persianDate " + persianDate);
        System.out.println("persian_Month " + persianMonth);
        System.out.println("persianDate1 " + persianDate1);
    }



    @Test
    public void getWalletByUserId() {
        RetrofitApiService api = ApiClient.getInstance().create(RetrofitApiService.class);
        api.getWalletByUserId(37L).enqueue(new Callback<List<HomeDto>>() {
            @Override
            public void onResponse(Call<List<HomeDto>> call, Response<List<HomeDto>> response) {

                if (response.isSuccessful()){
                    for (HomeDto w :response.body()){
                        if (w.getDefault()){
                            Gson g = new Gson();
                            g.toJson(w);
                            SingletonWalletInfo.getInstance().replace(w);
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<List<HomeDto>> call, Throwable t) {
                call.cancel();
            }
        });


        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
    }


    @Test
    public void prettySHow() {
         String CLIENT_ID = "7WM5HpvoLf0W.apps.keyob.com";
         String CLIENT_SECRET = "hXEKlX5hBm0IZ1eMB9V8tTGp2H6o9cqq";

        String sum =CLIENT_ID+CLIENT_SECRET;
        byte[] encrypt= sum.getBytes();
        String base64 = Base64.encodeToString(encrypt, Base64.DEFAULT);

        StringBuilder sb = new StringBuilder();
        sb.append("Basic");
        sb.append(" ");
        sb.append(base64);
        System.out.println(sb.toString());

    }





}
