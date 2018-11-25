package com.a1466387944.a2weimakabao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        BarcodeClass[] barcodeClasses = new BarcodeClass[2];
        barcodeClasses[0] = new BarcodeClass(null,"1st","the 1st");
        barcodeClasses[1] = new BarcodeClass(null,"2nd","YEEEES!");
        AdapterMain adapterMain = new AdapterMain(barcodeClasses);

        recyclerView = findViewById(R.id.recyclerview_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterMain);
    }
}
