package com.a1466387944.a2weimakabao;

import android.content.Context;

import java.util.ArrayList;

public interface MainActivityBarcodeManager {
    public ArrayList<BarcodeClass> getBarcodeArrayList();

    public void NotifyDataChanged();
}
