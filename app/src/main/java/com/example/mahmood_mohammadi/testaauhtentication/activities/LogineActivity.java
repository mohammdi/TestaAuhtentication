package com.example.mahmood_mohammadi.testaauhtentication.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.VolleyError;
import com.example.mahmood_mohammadi.testaauhtentication.ObjectModel.Users;
import com.example.mahmood_mohammadi.testaauhtentication.ObjectModel.Wallet;
import com.example.mahmood_mohammadi.testaauhtentication.R;
import com.example.mahmood_mohammadi.testaauhtentication.helper.ApiService;
import com.example.mahmood_mohammadi.testaauhtentication.helper.SQLiteHandler;
import com.example.mahmood_mohammadi.testaauhtentication.staticRepository.PutExtraKey;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LogineActivity extends AppCompatActivity {

    private ApiService apiService;
    private Context context = this;
    private EditText userName;
    private EditText password;
    private JSONObject jsonObject;
    private Users myuser = new Users();
    private List<Wallet> walletList;
    private Boolean online = true;
    private String extra_message_internet_statuse;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logine);

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        Button btn_register = (Button) findViewById(R.id.btnLinkToLoginScreen);

        apiService = new ApiService(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateRequest();
                if (!online) {
                    extra_message_internet_statuse = "connection not available!!!";
                }else if(myuser.getId() != null){

                    Intent intent = new Intent(LogineActivity.this, HomeActivity.class);

                    if (online && extra_message_internet_statuse != null) {
                        intent.putExtra(PutExtraKey.Message, extra_message_internet_statuse);
                    }else{

                        intent.putExtra(PutExtraKey.USERS ,myuser);
                    }

                    startActivity(intent);
                }


                /**
                 *send message if internet not available
                 */

            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogineActivity.this, ConfirmCodeActivity.class);
                startActivity(intent);
            }
        });
    }


    public void authenticateRequest() {

        Map<String, Users> params = new HashMap<>();
        final Users user = new Users();
        user.setUserName(userName.getText().toString());
        user.setPassword(password.getText().toString());
        params.put("users", user);
        jsonObject = new JSONObject(params);


        /**
         * check internet status
         **/
        online = statusNetWOrk();
        if (online) {

            try {
                apiService.Authenticate(new ApiService.OnResponsReceiveByJsonObject() {
                    @Override
                    public void recieve(JSONObject message) {
                        try {
                            SQLiteHandler sqLiteHandler = new SQLiteHandler(context);
                            token = (String) message.get("token");
                            myuser = (Users) message.get("users");
                            sqLiteHandler.addUser(user);
                            walletListRequset(myuser.getId());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(VolleyError message) {
                        Toast.makeText(context, String.valueOf(message), Toast.LENGTH_LONG);
                    }
                }, jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{

            Toast.makeText(context,"connection unavable",Toast.LENGTH_LONG).show();
        }

    }

    private void walletListRequset(final Long userId) {

        Map<String, Long> params = new HashMap<String, Long>();
        params.put("userId", userId);
        jsonObject = new JSONObject(params);

        apiService.getWalletByuserId(new ApiService.OnResponsReceiveByJsonObject() {
            @Override
            public void recieve(JSONObject message) {
                SQLiteHandler sqLiteHandler = new SQLiteHandler(context);
                try {

                    walletList = (List<Wallet>) message.get("wallets");
                    String str = (String) message.get("message");
                    if (walletList!=null){
                       for (Wallet w :walletList)
                        sqLiteHandler.addWallet(w, userId);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(VolleyError message) {
                Toast.makeText(context, String.valueOf(message), Toast.LENGTH_LONG);

            }
        }, jsonObject);
    }


    public Boolean statusNetWOrk() {

        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = connec.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            return true;

        } else {

            return false;

        }
    }


}
