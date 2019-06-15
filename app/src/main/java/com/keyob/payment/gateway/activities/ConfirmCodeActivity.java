package com.keyob.payment.gateway.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.keyob.payment.gateway.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConfirmCodeActivity extends AppCompatActivity {

    private Button submitbtn;
    private JSONObject jsonObject;
    private TextView confcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_code);

        confcode = (TextView) findViewById(R.id.confCode);
        submitbtn = (Button) findViewById(R.id.btn_submit);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = confcode.getText().toString();
                Intent intent = new Intent(ConfirmCodeActivity.this,GetDetaileActivity.class);
                startActivity(intent);
            }
        });
    }


}
