package com.keyob.payment.gateway.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersianDateCoordinatore {


    public static  String ConvertGregorianToPersian(String date){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date gregDate = null;
        try {
            gregDate = format.parse(date);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return CustomPersianCalendar.getPersianDate(gregDate);
    }


    public static  String ConvertGregorianToPersian(Date date){

        return CustomPersianCalendar.getPersianDate(date);
    }

    public static  String getTime(Date date){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String format = dateFormat.format(date);
       return format;
    }
}
