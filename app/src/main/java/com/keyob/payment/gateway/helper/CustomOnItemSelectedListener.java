package com.keyob.payment.gateway.helper;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
public class CustomOnItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(parent.getContext(),
//                "wallet Toast  : " + parent.getItemAtPosition(position).toString(),
//                Toast.LENGTH_SHORT).show();

        Snackbar  snackbar = Snackbar.make(view,"simple snack !!!",Snackbar.LENGTH_SHORT);
        snackbar.show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
