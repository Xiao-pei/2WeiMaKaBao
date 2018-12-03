package com.a1466387944.a2weimakabao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.gms.vision.L;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.camera.CameraManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<BarcodeClass> barcodeClasses;
    MainActivityBarcodeManager barcodeManager;
    AdapterMain adapterMain;
    ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        barcodeManager = BarcodeFileManager.getBarcodeFileManager(this);
        barcodeClasses = barcodeManager.getBarcodeArrayList();
        adapterMain = new AdapterMain(barcodeClasses);
        setAdapterlistener(adapterMain);

        recyclerView = findViewById(R.id.recyclerview_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterMain);

        ItemTouchHelperCallback callback = new ItemTouchHelperCallback(barcodeManager, adapterMain);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterMain.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_scan_barcode:
                Intent intent = new Intent(this, ScanActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setAdapterlistener(final AdapterMain adapterMain) {
        adapterMain.setStarButtonListener(new AdapterMain.MyClickItemListener() {
            @Override
            public void onClicked(View view, int position) {
                if (barcodeClasses.get(position).IsStared()) {
                    barcodeClasses.get(position).setIsStared(false);
                } else {
                    barcodeClasses.get(position).setIsStared(true);
                }
                Collections.sort(barcodeClasses);
                adapterMain.notifyDataSetChanged();
                barcodeManager.NotifyDataChanged();
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
