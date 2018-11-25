package com.a1466387944.a2weimakabao;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import java.util.Date;

public class BarcodeClass {
    private Bitmap barcode_bitbap;
    private String name;
    private String info;
    private Boolean is_stared;
    private Date created;
    private int expire; //Expire time in secs;

    public BarcodeClass(Bitmap bitmap, String name, @Nullable String info ) {
        barcode_bitbap = bitmap;
        this.name = name;
        this.info = info;
        created = new Date();
        is_stared = false;
    }

    public BarcodeClass(Bitmap bitmap, String name, @Nullable String info, int expire ) {
        barcode_bitbap = bitmap;
        this.name = name;
        this.info = info;
        created = new Date();
        this.expire = expire;
        is_stared = false;
    }

    public void setIsStared(Boolean is_stared) {
        this.is_stared = is_stared;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public Boolean getIsStared() {
        return is_stared;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public Bitmap getBarcode() {
        return barcode_bitbap;
    }

    public int getExpire() {
        return expire;
    }
}