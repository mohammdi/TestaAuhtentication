package com.keyob.payment.gateway.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.CustomPersianCalendar;
import com.keyob.payment.gateway.model.FromToDateModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.DATE_MODEL;

public class SelectDatePassBookActivity extends AppCompatActivity {

    private PersianDatePickerDialog picker;
    private Button fromDate;
    private Button toDate;
    private Button confirmDate;
    private PersianCalendar persianFromDate =new PersianCalendar();
    private PersianCalendar persianToDate;
    private Calendar gToDate;
    private Calendar gFromDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date_pass_book);
        fromDate = findViewById(R.id.passbook_from_date);
        toDate = findViewById(R.id.passbook_to_date);
        confirmDate = findViewById(R.id.passbook_confirm_date);

        PersianCalendar defaultPersianCalendar =new PersianCalendar();
        toDate.setText(defaultPersianCalendar.getPersianShortDate());
        fromDate.setText(defaultPersianCalendar.getPersianShortDate());

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialToDate();
            }
        });

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialFromDate();
            }
        });



        confirmDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (persianFromDate!=null){
                    gFromDate = CustomPersianCalendar.getGregorianCalendar(persianFromDate.getPersianYear(),
                            persianFromDate.getPersianMonth(),
                            persianFromDate.getPersianDay());
                }else {
                    gToDate = Calendar.getInstance();
                }
                if (persianToDate!=null){
                     gToDate = CustomPersianCalendar.getGregorianCalendar(persianToDate.getPersianYear(),
                            persianToDate.getPersianMonth(),
                            persianToDate.getPersianDay());
                }else {
                    gToDate = Calendar.getInstance();
                }


                Intent intent = new Intent(SelectDatePassBookActivity.this,PassBookListActivity.class);
                FromToDateModel dateModel = new FromToDateModel(gFromDate.getTime(),gToDate.getTime());
                intent.putExtra(DATE_MODEL,dateModel);
                startActivity(intent);
            }
        });

    }


    public void initialFromDate (){
        persianFromDate = new PersianCalendar();
        picker = new PersianDatePickerDialog(this)
                .setPositiveButtonString("انجام")
                .setNegativeButton("انصراف")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setInitDate(persianFromDate)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setMinYear(1300)
                .setActionTextColor(Color.GRAY)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar pDate) {
                        fromDate.setText(pDate.getPersianShortDate());
                        persianFromDate.setPersianDate(pDate.getPersianYear(), pDate.getPersianMonth(),pDate.getPersianDay());

                    }

                    @Override
                    public void onDismissed() {

                    }
                });

        picker.show();
    }

    public void initialToDate (){
       persianToDate = new PersianCalendar();
        picker = new PersianDatePickerDialog(this)
                .setPositiveButtonString("انجام")
                .setNegativeButton("انصراف")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setInitDate(persianToDate)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setMinYear(1300)
                .setActionTextColor(Color.GRAY)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar pDate) {
                        toDate.setText(pDate.getPersianShortDate());
                        persianToDate.setPersianDate(pDate.getPersianYear(),pDate.getPersianMonth(),pDate.getPersianDay());
                    }

                    @Override
                    public void onDismissed() {

                    }
                });

        picker.show();

    }


}
