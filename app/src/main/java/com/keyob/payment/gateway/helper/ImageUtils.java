package com.keyob.payment.gateway.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageUtils {


    public static Bitmap convert(String base64Str) throws IllegalArgumentException
    {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",")  + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convert(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }


//    public static File convert(String  base64) throws IOException {
//
//        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        decodedByte.compress(Bitmap.CompressFormat.JPEG, 70, stream);
//        byte[] byteFormat = stream.toByteArray();
//        stream.write(decodedString);
//        File file = new File();
//        return file;
//    }


}
