//package com.example.mahmood_mohammadi.testaauhtentication.helper.DataBase;
//
//import android.arch.persistence.room.Database;
//import android.arch.persistence.room.Room;
//import android.arch.persistence.room.RoomDatabase;
//import android.content.Context;
//
//import com.example.mahmood_mohammadi.testaauhtentication.dal.dao.UsersDao;
//import com.example.mahmood_mohammadi.testaauhtentication.dal.dao.WalletsDao;
//import com.example.mahmood_mohammadi.testaauhtentication.dal.model.Users;
//import com.example.mahmood_mohammadi.testaauhtentication.dal.model.Wallet;
//
//@Database(entities = {Users.class,Wallet.class}, version = 1)
//public abstract class AppDataBase  extends RoomDatabase {
//        private static AppDataBase INSTANCE;
//
//        public abstract UsersDao userDao();
//        public abstract WalletsDao walletsDao();
//
//
//        public static AppDataBase getAppDatabase(Context context) {
//            if (INSTANCE == null) {
//                INSTANCE =
//                        Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "my_keyob_db")
//                                .allowMainThreadQueries()
//                                .build();
//            }
//            return INSTANCE;
//        }
//
//        public static void destroyInstance() {
//            INSTANCE = null;
//        }
//    }
