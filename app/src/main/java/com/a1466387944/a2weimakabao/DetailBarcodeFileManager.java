package com.a1466387944.a2weimakabao;

public interface DetailBarcodeFileManager {
    void onItemDelete(int id);

    void BarcodeDataChanged(int id, String name, String info, int expire);
}
