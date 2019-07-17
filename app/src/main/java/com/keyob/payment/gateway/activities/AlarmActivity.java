package com.keyob.payment.gateway.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.keyob.payment.gateway.R;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.MESSAGE;

public class AlarmActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView  textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        imageView =findViewById(R.id.alarm_image);
        textView = findViewById(R.id.alarm_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MESSAGE);
        if (message.equals("MIS_NET")){
            imageView.setImageDrawable(getDrawable(R.drawable.ic_no_internet));
            textView.setText("اینترنت قطع  میباشد !");
        }

    }
}
