package com.a1466387944.a2weimakabao;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<BarcodeClass> barcodeClasses;
    private MainActivityBarcodeManager barcodeManager;
    private AdapterMain adapterMain;
    private ItemTouchHelper itemTouchHelper;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        barcodeManager = BarcodeFileManager.getBarcodeFileManager(this);
        barcodeClasses = barcodeManager.getBarcodeArrayList();
        adapterMain = new AdapterMain(barcodeClasses, findViewById(R.id.coordinator_layout));
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterMain.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapterMain.getFilter().filter(query);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterMain.reSyncList();
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
                ArrayList<BarcodeClass> filted_barcodes = adapterMain.getFilted_barcodeClasses();
                int id = filted_barcodes.get(position).getId();
                int changed_position = position;
                if (filted_barcodes.get(position).IsStared())
                    filted_barcodes.get(position).setIsStared(false);
                else
                    filted_barcodes.get(position).setIsStared(true);
                boolean result = filted_barcodes.get(position).IsStared();

                Collections.sort(filted_barcodes);
                for (int i = 0; i < filted_barcodes.size(); i++) {
                    if (filted_barcodes.get(i).getId() == id)
                        changed_position = i;
                }
                for (int i = 0; i < barcodeClasses.size(); i++) {
                    if (barcodeClasses.get(i).getId() == id)
                        barcodeClasses.get(i).setIsStared(result);
                }
                adapterMain.notifyItemChanged(position);
                adapterMain.notifyItemMoved(position, changed_position);
                Collections.sort(barcodeClasses);
                barcodeManager.NotifyDataChanged();
            }
        });

        adapterMain.setItemListener(new AdapterMain.MyClickItemListener() {
            @Override
            public void onClicked(View view, int position) {
                ArrayList<BarcodeClass> filted_barcodes = adapterMain.getFilted_barcodeClasses();
                Intent intent_to_detail = new Intent(view.getContext(), BarcodeDetail.class);
                BarcodeClass clicked_barcode = filted_barcodes.get(position);
                intent_to_detail.putExtra(BarcodeDetail.ID, clicked_barcode.getId());
                intent_to_detail.putExtra(BarcodeDetail.NAME, clicked_barcode.getName());
                intent_to_detail.putExtra(BarcodeDetail.INFO, clicked_barcode.getInfo());
                intent_to_detail.putExtra(BarcodeDetail.TYPE, clicked_barcode.getBarcode_type());
                intent_to_detail.putExtra(BarcodeDetail.CONTENT, clicked_barcode.getBarcode_content());
                startActivity(intent_to_detail);
            }
        });

        adapterMain.setSnackbarListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barcodeManager.UndoDelete();
                adapterMain.reSyncList();
            }
        });
    }
}
