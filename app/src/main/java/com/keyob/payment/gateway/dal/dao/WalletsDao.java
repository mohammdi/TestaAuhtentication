package com.keyob.payment.gateway.dal.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.keyob.payment.gateway.dal.model.Wallet;

import java.util.List;

@Dao
public interface WalletsDao {

    @Query("SELECT * FROM wallet ")
    List<Wallet> getAll();

    @Insert
    void insert (Wallet wallet);

    @Update
    void Update (Wallet wallet);

    @Delete
    void delete (Wallet wallet);

    @Query("SELECT * FROM wallet WHERE ID =:id")
    Wallet getOne(Long id);

    @Query("SELECT * from wallet where PUBLIC_ID =:publicId")
    Wallet findByPublicId(String publicId);
}
