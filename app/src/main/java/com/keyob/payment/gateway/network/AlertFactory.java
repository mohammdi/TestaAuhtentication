package com.keyob.payment.gateway.network;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class AlertFactory {

    private Context context;

    public AlertFactory(Context context) {
        this.context = context;
    }

    public void singleButtonAlert(String title, String message) {

            AlertDialog.Builder builderr = new AlertDialog.Builder(context);
            builderr.setTitle(title);
            builderr.setMessage(message);
            builderr.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builderr.create();
            alertDialog.show();
    }


    public void negetivButtonAlert(String title, String message) {

        AlertDialog.Builder builderr = new AlertDialog.Builder(context);
        builderr.setTitle(title);
        builderr.setMessage(message);
        builderr.setPositiveButton("بله", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderr.setNegativeButton("خیز", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builderr.create();
        alertDialog.show();
    }
}
