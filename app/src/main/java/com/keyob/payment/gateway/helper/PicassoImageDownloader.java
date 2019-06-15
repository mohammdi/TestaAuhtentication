package com.keyob.payment.gateway.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.keyob.payment.gateway.network.MyURLRepository;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PicassoImageDownloader {

    private static Context context;

    public static String  getImageUrl(Long walletId){
        StringBuilder url = new StringBuilder();
        url.append(MyURLRepository.GET_WALLET_LOGO);
        url.append(walletId);
        return url.toString();
    }

    public static void imageDownload(Context ctx,Long walletId,String imageName) {
        try {

            context = ctx;
            String url=getImageUrl(walletId);
            Picasso.with(ctx)
                    .load(url)
                    .into(getTarget(getFileName(imageName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getFileName(String fileName) {
        File file = new File(context.getFilesDir(),"keyob  ");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + fileName);
        return uriSting;

    }


    //target to save
    private static Target getTarget(final String fileName) {
        Target target = new Target() {

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            File file = new File(fileName);
                            if (file.exists()) {
                                file.delete();
                            }
                            boolean newFile = file.createNewFile();
                            FileOutputStream fileoutputstream = new FileOutputStream(file);
                            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 60, bytearrayoutputstream);
                            fileoutputstream.write(bytearrayoutputstream.toByteArray());
                            fileoutputstream.close();
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }

}
