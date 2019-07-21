package com.keyob.payment.gateway.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.SingletonUserInfo;
import com.keyob.payment.gateway.helper.URLAttacher;

public class ProfileActivity extends AppCompatActivity {

    private ImageView userImage;
    private ImageView backArrow;
    private TextView phoneNumber;
    private TextView fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        backArrow = findViewById(R.id.profile_back);
        userImage = findViewById(R.id.profile_userImage);
        phoneNumber = findViewById(R.id.profile_phoneNumber);
        fullName = findViewById(R.id.profile_fullName);


        SingletonUserInfo instance = SingletonUserInfo.getInstance();
        String strFullName = URLAttacher.doAttach(instance.getFirstName()," ", instance.getLastName());

        fullName.setText(strFullName);
        phoneNumber.setText(instance.getMobileNumber());


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slid_in_left,R.anim.slid_out_right);

            }
        });



    }



    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slid_in_left,R.anim.slid_out_right);
    }
}
