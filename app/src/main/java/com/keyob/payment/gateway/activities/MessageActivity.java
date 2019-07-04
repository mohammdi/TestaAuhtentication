package com.keyob.payment.gateway.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.keyob.payment.gateway.R;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.MESSAGE;

public class MessageActivity extends AppCompatActivity {

    private TextView messageViwe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        messageViwe = findViewById(R.id.message_text);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MESSAGE);
        messageViwe.setText(message);
        setUpToolBar();
    }

    private void setUpToolBar() {

        Toolbar toolbar = findViewById(R.id.message_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("صفحه اصلی ");
    }
}
