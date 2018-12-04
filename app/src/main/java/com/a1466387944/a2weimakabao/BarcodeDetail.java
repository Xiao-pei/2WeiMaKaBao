package com.a1466387944.a2weimakabao;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class BarcodeDetail extends AppCompatActivity {
    public static String ID = "id";
    public static String NAME = "name";
    public static String INFO = "info";
    public static String TYPE = "type";
    public static String CONTENT = "content";

    private String barcode_content;
    private String barcode_type;
    private String name;
    private String info;
    private int id;
    private Bitmap bitmap;
    private ImageView imageView;
    private TextView barcode_data;
    private EditText barcode_name;
    private EditText barcode_info;
    private boolean deleted;
    private DetailBarcodeFileManager barcodeFileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_detail);
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        deleted = false;
        imageView = findViewById(R.id.barcode_image_detail_page);
        barcode_data = findViewById(R.id.text_content_detail_page);
        barcode_name = findViewById(R.id.edit_text_name_detail_page);
        barcode_info = findViewById(R.id.edit_text_info_detail_page);
        barcodeFileManager = BarcodeFileManager.getBarcodeFileManager(this);
        Intent intent_from_main = getIntent();
        id = intent_from_main.getExtras().getInt(ID);
        name = intent_from_main.getStringExtra(NAME);
        info = intent_from_main.getStringExtra(INFO);
        barcode_content = intent_from_main.getStringExtra(CONTENT);
        barcode_type = intent_from_main.getStringExtra(TYPE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        barcode_name.setText(name);
        barcode_info.setText(info);
        bitmap = BarcodeGenerator.GenerateBarcodeBitmapFromData(barcode_content, barcode_type);
        if (BarcodeClass.isBarcodeAProduct(barcode_type)) {
            barcode_data.setText(barcode_content);
        } else {
            barcode_data.setText(barcode_type);
        }
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_barcode:
                DeleteBarcode();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!deleted)
            barcodeFileManager.BarcodeDataChanged(id, barcode_name.getText().toString(),
                    barcode_info.getText().toString());
    }

    private void DeleteBarcode() {
        barcodeFileManager.onItemDelete(id);
        deleted = true;
        Intent intent_to_main = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent_to_main);
    }
}
