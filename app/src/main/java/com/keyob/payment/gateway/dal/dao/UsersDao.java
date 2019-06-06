package com.keyob.payment.gateway.dal.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.keyob.payment.gateway.dal.model.Users;

import java.util.List;

@Dao
public interface UsersDao {

    @Query("SELECT * FROM user ")
    List<Users> getAll();

    @Insert
    void insert (Users user);

    @Update
    void Update (Users user);

    @Delete
    void delete (Users user);
}
