package com.example.mahmood_mohammadi.testaauhtentication.dal.l.model;

public class WalletType {

    private long id ;
    private String name ;

    public WalletType() {
    }

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
