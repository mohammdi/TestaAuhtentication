package com.keyob.payment.gateway.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.keyob.payment.gateway.dal.model.Wallet;
import com.keyob.payment.gateway.dal.model.WalletType;
import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.restClient.ApiService;
import com.keyob.payment.gateway.helper.spinnerManager.NothingSelectedSpinnerAdapter;
import com.keyob.payment.gateway.staticRepository.PutExtraKey;
import com.keyob.payment.gateway.helper.spinnerManager.SpinnerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateWalletActivity extends AppCompatActivity {


    private Spinner spinner ;
    private EditText wallet_name_ET;
    private EditText description_ET;
    private Button confirm_btn;
    private String  walletName ;
    private String  publcid ;
    private String  banner ;
    private Long  walletID ;
    private String  walletpass ;
    private String owner;
    private EditText passpayment_ET;
    private Long typeId;
    private String walletType;
    private JSONObject jsonObject ;

    private ApiService apiService = new ApiService(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);

//        ApiService apiService = new ApiService(this);

        owner = getIntent().getStringExtra(PutExtraKey.OWNER);


        Intent intent = getIntent();
//        spinner = (Spinner)findViewById(R.id.spinner_wallet_type);
        wallet_name_ET = (EditText) findViewById(R.id.walletName);
        description_ET = (EditText) findViewById(R.id.wallet_description);
        confirm_btn = (Button) findViewById(R.id.create_wallet_confirm);
        passpayment_ET =(EditText) findViewById(R.id.wallet_pass_Payment);


        owner = intent.getStringExtra(PutExtraKey.EDIT);
        walletName =intent.getStringExtra(PutExtraKey.WALLET_NAME);
        publcid = intent.getStringExtra(PutExtraKey.PUBLIC_ID);
        banner = intent.getStringExtra(PutExtraKey.BANNER);
        walletID = intent.getLongExtra(PutExtraKey.WALLET_ID ,0L);
        walletpass = intent.getStringExtra(PutExtraKey.WALLET_PASS);
        typeId = intent.getLongExtra(PutExtraKey.WALLET_TYPE,0L);



        if (owner!=null&& owner.equals(true)){
            wallet_name_ET.setText(walletName);
            if (typeId==1L){

                walletType ="personal";
            }
            else{

                walletType = "business";
            }

            spinner.setPrompt(walletType);

        }

        addItemTOSpinner();
        spinner.setEnabled(true);
        spinner.setClickable(true);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendRequest(walletName,walletID,walletpass,typeId);
            }
        });
    }



//    public void sendRequest(String walletName, Long  walletID,String walletpass,Long typeId){
//        Wallet wallet = new Wallet();
//        wallet.setId(walletID);
//        wallet.setName(walletName);
//        wallet.setPassPayment(walletpass);
////        wallet.setWalletType();
//
//        Map<String,Wallet> params = new HashMap<>();
//        params.put("wallet", wallet);
//        jsonObject= new JSONObject(params);
//        if (owner.equals(PutExtraKey.EDIT)){
//
//            apiService.updateWallet(new ApiService.OnResponseReceivedByJsonObject() {
//                @Override
//                public void receive(JSONObject message) {
//                    try {
//                        String msg = (String) message.get(message.toString());
//                        Toast.makeText(CreateWalletActivity.this,msg+"",Toast.LENGTH_LONG);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onError(VolleyError error) {
//                        Toast.makeText(CreateWalletActivity.this,error.getMessage(),Toast.LENGTH_LONG);
//
//                }
//            },jsonObject);
//
//        }else {
//
//            apiService.createWallet(new ApiService.OnResponseReceivedByJsonObject() {
//                @Override
//                public void receive(JSONObject message) {
//                    String msg = null;
//                    try {
//                        msg = (String) message.get(message.toString());
//                        Toast.makeText(CreateWalletActivity.this,msg+"",Toast.LENGTH_LONG);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//                @Override
//                public void onError(VolleyError message) {
//                        Toast.makeText(CreateWalletActivity.this,message.getMessage(),Toast.LENGTH_LONG);
//
//                }
//            },jsonObject);
//        }
//    }



    private void addItemTOSpinner() {

        List<WalletType> walletTypeList  = new ArrayList<>();
        WalletType wp1 =new WalletType();
        wp1.setId(1);
        wp1.setName("personal");

        WalletType wp2 = new WalletType();
        wp2.setId(2);
        wp2.setName("business");

        walletTypeList.add(wp1);
        walletTypeList.add(wp2);
        String[] array = {"personal","business"};
        SpinnerAdapter spinnerAdapter =new SpinnerAdapter(this,walletTypeList,array);
        spinner.setPrompt("select type wallet");
        spinner.setAdapter(new NothingSelectedSpinnerAdapter(spinnerAdapter,R.layout.nothing_spinner_selected,this));
    }

}