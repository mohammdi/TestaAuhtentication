


    /*
     * Author: mahmood mohammadi
     *
     */
package com.example.mahmood_mohammadi.testaauhtentication.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mahmood_mohammadi.testaauhtentication.dal.l.model.Users;
import com.example.mahmood_mohammadi.testaauhtentication.dal.l.model.Wallet;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_WALLET = "wallet";

    // Login Table Columns names
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_NAME = "name";
    private static final String KEY_USER_FAMILY = "family";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_MOBILE_NUMBER = "mobileNumber";
    private static final String KEY_USER_CREATED_DATE = "createDate";
    private static final String KEY_USER_PASSWORD= "password";


    // Table column wallet
    private static final String KEY_WALLET_ID = "id";
    private static final String KEY_WALLET_NAME = "name";
    private static final String KEY_WALLET_WALLET_TYPE = "typeId";
    private static final String KEY_WALLET_WALLET_USER_ID = "userId";
    private static final String Key_WALLET_IS_DEFAULT = "isDefault";
    private static final String KEY_WALLET_IS_SELECTED = "isSelected";
    private static final String KEY_WALLET_PASS_PAYMENT = "passPayment";
    private static final String KEY_WALLET_PUBLIC_ID = "publicId";
    private static final String KEY_WALLET_ADDRESS = "address";
    private static final String KEY_WALLET_LOGO = "logo";
    private static final String KEY_WALLET_BANNER = "banner";
    private static final String KEY_WALLET_CREATE_DATE = "createDate";



    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
                + KEY_USER_ID + " INTEGER PRIMARY KEY ," + KEY_USER_NAME + " TEXT , " + KEY_USER_FAMILY + " TEXT , "
                + KEY_USER_EMAIL + " TEXT UNIQUE ," + KEY_USER_MOBILE_NUMBER + " TEXT UNIQUE , "
                + KEY_USER_CREATED_DATE + " DATE , "
                + KEY_USER_PASSWORD + " TEXT "+")";

        String CREATE_LOGIN_TABLE_WALLET = "CREATE TABLE " + TABLE_WALLET + "("
                + KEY_WALLET_ID + " INTEGER PRIMARY KEY," + KEY_WALLET_NAME + " TEXT ,"
                + KEY_WALLET_PUBLIC_ID + " TEXT UNIQUE ," + KEY_WALLET_CREATE_DATE + " TEXT ,"
                + KEY_WALLET_PASS_PAYMENT + " TEXT ," + Key_WALLET_IS_DEFAULT + " boolean DEFAULT 0,"
                + KEY_WALLET_IS_SELECTED + " boolean DEFAULT 0 , " + KEY_WALLET_ADDRESS + " TEXT, "
                + KEY_WALLET_BANNER + " TEXT ," + KEY_WALLET_LOGO + " TEXT ," + KEY_WALLET_WALLET_TYPE + " INTEGER ,"
                + KEY_WALLET_WALLET_USER_ID +" INTEGER "+")";

        db.setVersion(1);
        db.execSQL(CREATE_LOGIN_TABLE_USER);
        Log.d(TAG, "user tables created" +db.getAttachedDbs()+"_"+db.getSyncedTables());

        db.execSQL(CREATE_LOGIN_TABLE_WALLET);

        Log.d(TAG, "Database tables created" +db.getPath()+"_"+db.getVersion());
        Log.d(TAG,  "db_ Path :"+db.getPath()+"\n db_version :"+db.getVersion()+"\n ");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_WALLET);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     */
    public Long  addUser(Users users) {
        SQLiteDatabase db = this.getWritableDatabase();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID ,1L);
        values.put(KEY_USER_NAME, users.getName()); // Name
        values.put(KEY_USER_FAMILY, users.getFamily()); // family
        values.put(KEY_USER_EMAIL, users.getEmail()); // Email
        values.put(KEY_USER_MOBILE_NUMBER, users.getMobileNumber()); //mobile number
        values.put(KEY_USER_CREATED_DATE,date.toString()); // create Date
//        values.put(KEY_USER_PASSWORD ,users.getPassword());
        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);

        return id ;
    }


    public void addToken(String token ,Long userId) {
        //Todo save Token in token join Table (token , user id )
    }


    public Boolean addWallet(Wallet wallet ,Long userId) {
        SQLiteDatabase db = this.getWritableDatabase();

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
        w_values.put(Key_WALLET_IS_DEFAULT, wallet.getUserId());

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

    /**
     * Getting user data from database
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("id",cursor.getString(0));
            user.put("name", cursor.getString(1));
            user.put("family", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("mobileNumber", cursor.getString(4));
            user.put("createDate", cursor.getString(5));
            user.put("password",cursor.getString(6));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}
