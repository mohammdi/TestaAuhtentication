package com.keyob.payment.gateway.helper;

public class URLAttacher {



    public static  String doAttach (String part1 , String part2 ,String part3){

        StringBuilder sb = new StringBuilder();

        if (part1!=null){

            sb.append(part1);
        }
        if (part2!=null){

            sb.append(part2);
        }
        if (part3!=null){

            sb.append(part3);
        }
        return sb.toString();
    }
}
