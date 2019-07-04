package com.keyob.payment.gateway;

import com.keyob.payment.gateway.helper.CustomPersianCalendar;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        String dtStart = "Sat Jun 22 13:25:34 GMT+04:30 2019";
        String str ="2019-06-29T12:06:01.833";
        Date date = new Date();
        System.out.println(date);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Locale locale = new Locale("USA");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-DD HH:MM:SS", locale);
//        Date date = null;
//        date = new date.(dtStart);
        String persianDate = CustomPersianCalendar.getPersianDate(date);
        int persianMonth = CustomPersianCalendar.getPersianMonth(date);
        String persianDate1 = CustomPersianCalendar.getPersianDate(date);
        System.out.println("persianDate " + persianDate);
        System.out.println("persian_Month " + persianMonth);
        System.out.println("persianDate1 " + persianDate1);
    }

    @Test
    public void prettySHow() {

        String str ="282d338d-c95c-4413-9834-6bfbf7e9f8c0";
        String str2 ="26f3bf28-902e-474d-9226-7505631858a4";
    }


}
