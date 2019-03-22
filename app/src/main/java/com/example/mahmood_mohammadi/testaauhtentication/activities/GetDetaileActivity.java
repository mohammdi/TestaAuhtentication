package com.example.mahmood_mohammadi.testaauhtentication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmood_mohammadi.testaauhtentication.R;

public class GetDetaileActivity extends AppCompatActivity {
    private TextView input_email;
    private TextView input_name;
    private TextView input_familly;
    private TextView input_pass;
    private Button detaile_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_detaile);

        detaile_submit = (Button)findViewById(R.id.btn_detail_Register);
        input_name =(TextView)findViewById(R.id.userName) ;
        input_pass =(TextView)findViewById(R.id.password);
        input_email =(TextView)findViewById(R.id.email);
        input_familly = (TextView)findViewById(R.id.family);


        detaile_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = input_email.getText().toString();
                String password = input_pass.getText().toString();
                String name= input_name.getText().toString();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }



                Intent intent = new Intent(GetDetaileActivity.this,HomeActivity.class);

                intent.putExtra("name",name);
                intent.putExtra("email",email);
                intent.putExtra("pass",password);

                startActivity(intent);
                finish();
            }
        });

    }
}
