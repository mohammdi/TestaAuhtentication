package com.keyob.payment.gateway.dal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.keyob.payment.gateway.dal.model.Wallet;
import com.keyob.payment.gateway.dal.model.WalletDTO;
import com.keyob.payment.gateway.helper.DataBase.SQLiteHandler;

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

    private  Context context;
    private SQLiteHandler sqLiteHandler;
    public WalletDao(Context context) {
        sqLiteHandler = new SQLiteHandler(context);
    }

    public Boolean addWallet(Wallet wallet , Long userId) {
        SQLiteDatabase db = sqLiteHandler.getWritableDatabase();

        ContentValues w_values = new ContentValues();
        w_values.put(KEY_WALLET_ADDRESS, wallet.getAddress()); // address
        w_values.put(KEY_WALLET_NAME, wallet.getName()); // name
        w_values.put(KEY_WALLET_PUBLIC_ID, wallet.getPublicId()); // Public ID
        w_values.put(KEY_WALLET_PASS_PAYMENT, wallet.getPassPayment()); //pass Payment
//        w_values.put(KEY_WALLET_WALLET_TYPE, wallet.getWalletType().getId()); //type Id
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


    public WalletDTO findById(String public_id){
        WalletDTO walletDTO = new WalletDTO();
        SQLiteDatabase db = sqLiteHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_WALLET +" WHERE publicId =?",new String[]{public_id});
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            walletDTO.setId(cursor.getLong(cursor.getColumnIndex(KEY_WALLET_ID)));
            walletDTO.setName(cursor.getString(cursor.getColumnIndex(KEY_WALLET_NAME)));
            walletDTO.setPublicId(cursor.getString(cursor.getColumnIndex(KEY_WALLET_PUBLIC_ID)));
            walletDTO.setPassPayment(cursor.getString(cursor.getColumnIndex(KEY_WALLET_PASS_PAYMENT)));
            walletDTO.setTypeId(cursor.getLong(cursor.getColumnIndex(KEY_WALLET_WALLET_TYPE)));
            walletDTO.setBannerPath(cursor.getString(cursor.getColumnIndex(KEY_WALLET_BANNER)));
            walletDTO.setLogoPath(cursor.getString(cursor.getColumnIndex(KEY_WALLET_LOGO)));
            walletDTO.setIsSelected(cursor.getInt(cursor.getColumnIndex(KEY_WALLET_IS_SELECTED)));
            walletDTO.setIsDefault(cursor.getInt(cursor.getColumnIndex(Key_WALLET_IS_DEFAULT)));
            walletDTO.setUserId( cursor.getLong(cursor.getColumnIndex(KEY_WALLET_WALLET_USER_ID)));
            walletDTO.setAddress(cursor.getString(cursor.getColumnIndex(KEY_WALLET_ADDRESS)));
        }

        if (walletDTO.getId()!=null){
            Log.i(TAG, "wallet Exists by id = "+walletDTO.getId());
        }else {
            Log.i(TAG,"wallet not Exists in dataBse ");
        }
        return walletDTO;
    }

     /*Getting wallet data from database*/
     public List<HashMap<String, String>> getWalletList() {
         List<HashMap<String,String>> walletList = new ArrayList<>();
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
        SQLiteDatabase db = sqLiteHandler.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_WALLET, "id=", new String[]{String.valueOf(walletId)});
        db.close();
        Log.d(TAG, "Deleted all user info from sqlLite");
    }


    public Long update (Wallet wallet){
        SQLiteDatabase db = sqLiteHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WALLET_ID,wallet.getId());
        values.put(KEY_WALLET_NAME,wallet.getName());
        values.put(KEY_WALLET_PUBLIC_ID,wallet.getPublicId());
        values.put(KEY_WALLET_PASS_PAYMENT,wallet.getPassPayment());
        values.put(KEY_WALLET_ADDRESS,wallet.getAddress());
//        values.put(KEY_WALLET_LOGO,wallet.getLogoPath());
//        values.put(KEY_WALLET_BANNER,wallet.getBannerPath());
        values.put(KEY_WALLET_IS_SELECTED,wallet.getSelected());
        values.put(KEY_WALLET_WALLET_USER_ID,wallet.getUserId());
//        values.put(KEY_WALLET_WALLET_TYPE,wallet.getWalletType().getId());

      db.update(TABLE_WALLET, values, "id =? ", new String[]{String.valueOf(wallet.getId())});
        Log.i(TAG,"wallet Updated...");
      return wallet.getId() ;

    }


}
