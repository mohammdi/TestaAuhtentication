package com.keyob.payment.gateway.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName ="wallet_type")

public class WalletType {

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    private long id ;

    @SerializedName("name")
    @ColumnInfo(name = "NAME")
    private String name ;

    public WalletType() {
    }

    @Ignore
    public WalletType(long id, String name) {
        this.id = id;
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
