package com.a1466387944.a2weimakabao;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.Calendar;
import java.util.Date;

public class BarcodeClass implements Comparable<BarcodeClass> {

    public static int ONE_DAY = 24 * 60;
    private String name;
    final private int id;
    private String info;
    private Boolean is_stared;
    private Date created;
    private int expire; //Expire time in min;
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

    public void setName(String name) {
        this.name = name;
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

    public Date getCreatedDate() {
        return created;
    }

    public boolean isExpired() {
        if (expire < 0)
            return false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(created);
        calendar.add(Calendar.MINUTE, expire);
        Date expire_date = calendar.getTime();
        if (expire_date.before(new Date()))
            return true;
        else
            return false;
    }

    public int getId() {
        return id;
    }

    public static Boolean isBarcodeAProduct(String barcodetype) {
        if (IntentIntegrator.PRODUCT_CODE_TYPES.contains(barcodetype)
                || barcodetype == BarcodeFormat.CODE_39.toString())
            return true;
        else
            return false;
    }

    @Override
    public int compareTo(@NonNull BarcodeClass barcodeClass) {
        if (isExpired() && barcodeClass.isExpired() || (!isExpired() && !barcodeClass.isExpired())) {
            if (is_stared && barcodeClass.IsStared() || (!is_stared && !barcodeClass.IsStared())) {
                if (created.after(barcodeClass.getCreatedDate()))
                    return -1;
                else
                    return 1;
            } else {
                if (is_stared)
                    return -1;
                else
                    return 1;
            }
        } else {
            if (isExpired())
                return 1;
            else
                return -1;
        }

    }
}
