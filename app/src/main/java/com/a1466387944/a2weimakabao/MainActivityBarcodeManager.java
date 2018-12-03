package com.a1466387944.a2weimakabao;

import android.content.Context;

import java.util.ArrayList;

public interface MainActivityBarcodeManager {
    ArrayList<BarcodeClass> getBarcodeArrayList();

    void NotifyDataChanged();

    void onItemDelete(int position);
}
