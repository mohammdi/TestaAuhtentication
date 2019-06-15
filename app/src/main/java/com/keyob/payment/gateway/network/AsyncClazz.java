package com.keyob.payment.gateway.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Mahmood_mohammadi on 11/7/2018.
 */

    public class AsyncClazz extends AsyncTask<String ,String,String> {
        public static String Log_Lable="mahmood";
        private static final String MY_URL ="http://31.184.135.14:8080/wallet/rest/wallets";
        public static String data = "";
        private Context context;
        public AsyncClazz(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            OutputStreamWriter outStream;
            InputStream MyStream;
            URL myurl = null;

            /**
             check internet in available or nat
             */
            boolean internetOn = statusNetWOrk();
            if (internetOn) {

                try {

                    myurl = new URL(MY_URL);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) myurl.openConnection();
                    connection.setReadTimeout(10000);
                    connection.setConnectTimeout(10000);
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Accept","application/json");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setDoInput(true);


                } catch (IOException e) {
                    e.printStackTrace();
                }



                int statuss = 0;
                for ( int i= 0 ; i<1 ;i++){

                    try {

                        connection.connect();
                        Writer writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                        writer.write(String.valueOf(params));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {

                        statuss = connection.getResponseCode();

                    } catch (IOException e) {

                        e.printStackTrace();
                    }


                    if (statuss == 200) {
                        break;
                    } else {
                        data = "vpn";
                        break;
                    }
                }


                try {
                    MyStream = connection.getInputStream();
                    BufferedReader Br = new BufferedReader(new InputStreamReader(MyStream));
                    StringBuilder sb = new StringBuilder();
                    String temp;
                    while ((temp = Br.readLine()) != null) {

                        sb.append(temp);
                    }
                    data = sb.toString();

                    MyStream.close();
                    Br.close();
                    connection.disconnect();

                } catch (IOException e) {
                    e.printStackTrace();
                }



            } else {


                data = "free";
            }


            return data;

        }


    @Override
    protected void onPostExecute(String s) {


    }

    public Boolean statusNetWOrk(){

            ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo netInfo = connec.getActiveNetworkInfo();

            if (netInfo != null && netInfo.isConnectedOrConnecting()) {

                return true;

            } else {

                return  false;

            }
    }

}
