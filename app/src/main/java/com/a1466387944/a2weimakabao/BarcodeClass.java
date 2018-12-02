package com.a1466387944.a2weimakabao;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.Date;

public class BarcodeClass {

    private String name;
    private int id;
    private String info;
    private Boolean is_stared;
    private Date created;
    private int expire; //Expire time in secs;
    private String barcode_content;
    private String barcode_type;


    public BarcodeClass(String name, @Nullable String info, String barcode_content, String barcode_type, int expire) {
        this.name = name;
        this.info = info;
        created = new Date();
        this.expire = expire;
        is_stared = false;
        id = created.hashCode();
        this.barcode_content = barcode_content;
        this.barcode_type = barcode_type;
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

    public Boolean IsStared() {
        return is_stared;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public int getExpire() {
        return expire;
    }

    public String getBarcode_content() {
        return barcode_content;
    }

    public String getBarcode_type() {
        return barcode_type;
    }


    public boolean isExpired() {
        if (expire < 0)
            return false;
        Date expire_date = new Date(created.getTime() + expire * 1000);
        if (expire_date.before(new Date()))
            return true;
        else return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Boolean isBarcodeAProduct(String barcodetype) {
        if (IntentIntegrator.PRODUCT_CODE_TYPES.contains(barcodetype))
            return true;
        else
            return false;
    }
}
