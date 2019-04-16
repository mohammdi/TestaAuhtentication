package com.example.mahmood_mohammadi.testaauhtentication.dal.l.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mahmood_mohammadi.testaauhtentication.dal.l.model.Wallet;
import com.example.mahmood_mohammadi.testaauhtentication.helper.SQLiteHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WalletDao {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_WALLET = "wallet";

    //  Table Columns names
    public  static final String KEY_WALLET_ID = "id";
    public static final String KEY_WALLET_NAME = "name";
    public static final String KEY_WALLET_WALLET_TYPE = "typeId";
    public static final String KEY_WALLET_WALLET_USER_ID = "userId";
    public static final String Key_WALLET_IS_DEFAULT = "isDefault";
    public static final String KEY_WALLET_IS_SELECTED = "isSelected";
    public static final String KEY_WALLET_PASS_PAYMENT = "passPayment";
    public static final String KEY_WALLET_PUBLIC_ID = "publicId";
    public static final String KEY_WALLET_ADDRESS = "address";
    public static final String KEY_WALLET_LOGO = "logo";
    public static final String KEY_WALLET_BANNER = "banner";
    public static final String KEY_WALLET_CREATE_DATE = "createDate";

    Context context;

    public WalletDao(Context context) {
        this.context = context;
    }

    public Boolean addWallet(Wallet wallet , Long userId) {
        SQLiteHandler sqLiteHandler = new SQLiteHandler(context);
        SQLiteDatabase db = sqLiteHandler.getWritableDatabase();

        ContentValues w_values = new ContentValues();
        w_values.put(KEY_WALLET_ADDRESS, wallet.getAddress()); // address
        w_values.put(KEY_WALLET_NAME, wallet.getName()); // name
        w_values.put(KEY_WALLET_PUBLIC_ID, wallet.getPublicId()); // Public ID
        w_values.put(KEY_WALLET_PASS_PAYMENT, wallet.getPassPayment()); //pass Payment
        w_values.put(KEY_WALLET_WALLET_TYPE, wallet.getWalletType().getId()); //type Id
        w_values.put(KEY_WALLET_BANNER, String.valueOf(wallet.getBannerPath())); //pass Payment
        w_values.put(KEY_WALLET_LOGO, String.valueOf(wallet.getLogoPath())); //pass Payment
        w_values.put(KEY_WALLET_IS_SELECTED, wallet.getSelected());
        w_values.put(Key_WALLET_IS_DEFAULT, wallet.getDefault());
        w_values.put(KEY_WALLET_WALLET_USER_ID,wallet.getUserId());
        w_values.put(KEY_WALLET_CREATE_DATE,wallet.getCreateDate());

        // Inserting Row
        long id = db.insert(TABLE_WALLET, null, w_values);
        db.close(); // Closing database connection


        if (id > 0) {

            Log.d(TAG, "New wallet inserted into sqlite: " + id);
            return true;

        } else {

            return false;
        }

    }


     /*Getting wallet data from database*/
     public List<HashMap<String, String>> getWalletList() {
         List<HashMap<String,String>> walletList = new ArrayList<>();
         SQLiteHandler sqLiteHandler = new SQLiteHandler(context);
         HashMap<String, String> wallets = new HashMap<String, String>();
         String selectQuery = "SELECT  * FROM " + TABLE_WALLET;

         SQLiteDatabase db = sqLiteHandler.getReadableDatabase();
         Cursor cursor = db.rawQuery(selectQuery, null);
         // Move to first row
         cursor.moveToFirst();
         while (cursor.moveToNext()) {
             wallets.put(KEY_WALLET_ID, cursor.getString(cursor.getColumnIndex(KEY_WALLET_ID)));
             wallets.put(KEY_WALLET_NAME, cursor.getString(cursor.getColumnIndex(KEY_WALLET_NAME)));
             wallets.put(KEY_WALLET_PUBLIC_ID, cursor.getString(cursor.getColumnIndex(KEY_WALLET_PUBLIC_ID)));
             wallets.put(KEY_WALLET_PASS_PAYMENT, cursor.getString(cursor.getColumnIndex(KEY_WALLET_PASS_PAYMENT)));
             wallets.put(KEY_WALLET_WALLET_TYPE, cursor.getString(cursor.getColumnIndex(KEY_WALLET_WALLET_TYPE)));
             wallets.put(KEY_WALLET_BANNER, cursor.getString(cursor.getColumnIndex(KEY_WALLET_BANNER)));
             wallets.put(KEY_WALLET_LOGO, cursor.getString(cursor.getColumnIndex(KEY_WALLET_LOGO)));
             wallets.put(KEY_WALLET_IS_SELECTED, cursor.getString(cursor.getColumnIndex(KEY_WALLET_IS_SELECTED)));
             wallets.put(Key_WALLET_IS_DEFAULT, cursor.getString(cursor.getColumnIndex(Key_WALLET_IS_DEFAULT)));
             wallets.put(KEY_WALLET_WALLET_USER_ID, cursor.getString(cursor.getColumnIndex(KEY_WALLET_WALLET_USER_ID)));
             wallets.put(KEY_WALLET_ADDRESS, cursor.getString(cursor.getColumnIndex(KEY_WALLET_ADDRESS)));
             walletList.add(wallets);
         }

         return walletList;
     }

   /*Re crate database Delete all tables and create them again*/

    public void delete(Long walletId) {
        SQLiteHandler sqLiteHandler = new SQLiteHandler(context);
        SQLiteDatabase db = sqLiteHandler.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_WALLET, "id=", new String[]{String.valueOf(walletId)});
        db.close();
        Log.d(TAG, "Deleted all user info from sqlLite");
    }


}
