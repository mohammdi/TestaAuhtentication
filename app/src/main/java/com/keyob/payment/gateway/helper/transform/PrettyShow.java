package com.keyob.payment.gateway.helper.transform;


import java.text.DecimalFormat;

public class PrettyShow {

    public static String seperateZero(Integer balance){

        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        return formatter.format(balance);

    }
}
