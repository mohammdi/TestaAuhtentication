package com.example.mahmood_mohammadi.testaauhtentication.dal.l.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mahmood_mohammadi.testaauhtentication.dal.l.model.Users;
import com.example.mahmood_mohammadi.testaauhtentication.helper.SQLiteHandler;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class UserDao {

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
    private static final String KEY_USER_PASSWORD = "password";

    Context context;

    public UserDao(Context context) {
        this.context = context;
    }






    public Long saveUser(Users users) {

        SQLiteHandler sqLiteHandler = new SQLiteHandler(context);
        SQLiteDatabase db = sqLiteHandler.getWritableDatabase();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, 1L);
        values.put(KEY_USER_NAME, users.getName()); // Name
        values.put(KEY_USER_FAMILY, users.getFamily()); // family
        values.put(KEY_USER_EMAIL, users.getEmail()); // Email
        values.put(KEY_USER_MOBILE_NUMBER, users.getMobileNumber()); //mobile number
        values.put(KEY_USER_CREATED_DATE, date.toString()); // create Date
        values.put(KEY_USER_PASSWORD, users.getPassword());
        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);

        return id;
    }


     /*Getting user data from database*/

    public HashMap<String, String> getUser() {

        SQLiteHandler sqLiteHandler = new SQLiteHandler(context);
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = sqLiteHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("id", cursor.getString(0));
            user.put("name", cursor.getString(1));
            user.put("family", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("mobileNumber", cursor.getString(4));
            user.put("createDate", cursor.getString(5));
            user.put("password", cursor.getString(6));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }



   /*Re crate database Delete all tables and create them again*/

    public void delete() {

        SQLiteHandler sqLiteHandler = new SQLiteHandler(context);
        SQLiteDatabase db = sqLiteHandler.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }


}
