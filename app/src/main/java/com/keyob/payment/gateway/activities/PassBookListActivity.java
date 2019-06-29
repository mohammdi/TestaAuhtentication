package com.keyob.payment.gateway.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.model.FromToDateModel;

import java.util.Date;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.DATE_MODEL;

public class PassBookListActivity extends AppCompatActivity {

     private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_book_list);

        Intent intent = getIntent();
        FromToDateModel model = (FromToDateModel) intent.getSerializableExtra(DATE_MODEL);
        Date fromDate = model.getFromDate();
        Date toDate = model.getToDate();
        Toast.makeText(this, fromDate+"-------------->"+toDate, Toast.LENGTH_SHORT).show();



    }

}
