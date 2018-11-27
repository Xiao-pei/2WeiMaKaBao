package com.a1466387944.a2weimakabao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BarcodeClass[] barcodeClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        barcodeClasses = new BarcodeClass[2];
        barcodeClasses[0] = new BarcodeClass(null, "1st", "the 1st");
        barcodeClasses[1] = new BarcodeClass(null, "2nd", "YEEEES!");
        barcodeClasses[0].setIsStared(true);

        AdapterMain adapterMain = new AdapterMain(barcodeClasses);
        setAdapterlistener(adapterMain);

        recyclerView = findViewById(R.id.recyclerview_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterMain);
    }

    private void setAdapterlistener(final AdapterMain adapterMain) {
        adapterMain.setStarButtonListener(new AdapterMain.MyClickItemListener() {
            @Override
            public void onClicked(View view, int position) {
                if (barcodeClasses[position].IsStared()) {
                    barcodeClasses[position].setIsStared(false);
                } else {
                    barcodeClasses[position].setIsStared(true);
                }
                adapterMain.notifyItemChanged(position);
                Toast toast = Toast.makeText(MainActivity.this, "clicked star " + position, Toast.LENGTH_SHORT);
                toast.show();

            }
        });

        adapterMain.setItemListener(new AdapterMain.MyClickItemListener() {
            @Override
            public void onClicked(View view, int position) {
                Toast toast = Toast.makeText(MainActivity.this, "clicked " + position, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
