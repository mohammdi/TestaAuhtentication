package com.example.mahmood_mohammadi.testaauhtentication.helper;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mahmood_mohammadi.testaauhtentication.staticRepository.MyURLRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Mahmood_mohammadi on 11/6/2018.
 */

public class ApiService {

    private Context context;
    private RequestQueue requestQueue;


    public ApiService(Context context) {
        this.context = context;
    }


    public void Authenticate (final OnResponseReceivedByJsonObject onRespons , JSONObject credentioan) throws Exception {
        requestQueue = Volley.newRequestQueue(context);

        try {
            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, MyURLRepository.GET_BY_USER_ID_WALLET_URL,credentioan, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        onRespons.receive(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        onRespons.onError(error);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }

            });


            jsonObj.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            jsonObj.getBody();
            jsonObj.getHeaders();
            requestQueue.add(jsonObj);
        } catch (Exception e) {
            throw e ;
        }

    }

    public void getConFirmCode(final OnResponsReceive confirmCodeReceive, JSONObject email) {
        requestQueue = Volley.newRequestQueue(context);
        String url = "http://192.168.1.102:8081/rest/user/cash/putPhone/json";
        try {

            JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.POST, url, email,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String message = parsToMessage(response);
                            confirmCodeReceive.receive(message);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    confirmCodeReceive.onError();
                }
            });

            jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            jsonObjRequest.getBody();
            jsonObjRequest.getHeaders();
            requestQueue.add(jsonObjRequest);

        } catch (Exception e) {
            confirmCodeReceive.onError();
        }


    }

    public void getWallets(final OnResponseReceivedByJsonObject responsReceive) {
        requestQueue = Volley.newRequestQueue(context);
        try {
            final JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, MyURLRepository.GET_BY_USER_ID_WALLET_URL, null,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        responsReceive.receive(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    responsReceive.onError(error);

                }
            });


            jsonObj.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            jsonObj.getBody();
            jsonObj.getHeaders();
            requestQueue.add(jsonObj);

        } catch (Exception e) {

            /**
             *  maybe produce ClassCastException
             */
            responsReceive.onError((VolleyError) e);
        }
    }


    public void createWallet(final OnResponseReceivedByJsonObject createWallet_ResponsReceive, JSONObject wallet_json) {

        requestQueue = Volley.newRequestQueue(context);
//        String url ="http://192.168.1.101:8081/rest/filter/create/wallet";
        try {

            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, MyURLRepository.CREATE_WALLET_URL, wallet_json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

        } catch (Exception e) {

        }

    }


    public void updateWallet(final OnResponseReceivedByJsonObject updateWallet_responseReceive, JSONObject wallet_json) {
        requestQueue = Volley.newRequestQueue(context);
//        String url = "http://192.168.1.101:8081/rest/filter/update/wallet";

        try {
            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.PUT, MyURLRepository.UPDATE_WALLET_URL, wallet_json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

        } catch (Exception e) {

        }


    }


    public void deleteWallet(final OnResponsReceive deleteWalletRepReq, JSONObject walletId_json) {
        requestQueue = Volley.newRequestQueue(context);

        try {

            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.DELETE, MyURLRepository.DELETE_WALLET_URL, walletId_json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

        } catch (Exception e) {


        }
    }


    public void getConFirmCode2(final OnResponsReceive confirmCodeReceive, String email) throws ExecutionException, InterruptedException {

        AsyncClazz asyncClazz = new AsyncClazz(context);
        asyncClazz.execute(String.valueOf(email));
    }


    public void verificationConfCode(final OnResponsReceive onResponsReceive, JSONObject jsonObject) {

        requestQueue = Volley.newRequestQueue(context);
        String url = "My URL";
        try {

            JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String message = parsToMessage(response);
                            onResponsReceive.receive(message);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    onResponsReceive.onError();
                }
            });

            jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            jsonObjRequest.getBody();
            jsonObjRequest.getHeaders();
            requestQueue.add(jsonObjRequest);

        } catch (Exception e) {
            onResponsReceive.onError();
        }
    }

    public String parsToMessage(JSONObject jsonObject) {
        String message = null;

        try {
            message = (String) jsonObject.get("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message;
    }


    public interface OnResponseReceivedByJsonObject {

        void receive(JSONObject message) throws JSONException;

        void onError(VolleyError message);
    }

    public interface OnResponseReceivedByJsonArray {

        void receive(JSONArray message) throws JSONException;

        void onError(VolleyError message);
    }


    public interface OnResponsReceive {

        void receive(String message);

        void onError();
    }


}
