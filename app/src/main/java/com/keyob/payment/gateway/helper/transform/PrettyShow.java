package com.keyob.payment.gateway.helper.transform;


import java.text.DecimalFormat;

public class PrettyShow {

    public static String separatedZero(Integer balance){

        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        return formatter.format(balance);

    }

    public static String separatedPublicId(String publicId){
        String delimiter=" ";
        String pretty = publicId.replaceAll(".{4}(?!$)", "$0" + delimiter);
        return pretty;
    }
}
