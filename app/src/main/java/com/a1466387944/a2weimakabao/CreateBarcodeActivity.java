package com.a1466387944.a2weimakabao;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.MenuItemCompat;
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
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class CreateBarcodeActivity extends AppCompatActivity {

    public static String FORMAT = "format";
    public static String DATA = "data";

    String barcode_data;
    String barcode_type;
    Bitmap bitmap;
    ImageView imageView;
    TextView barcode_content;
    EditText barcode_name;
    EditText barcode_info;
    NewBarcodeFileSaver file_saver;
    ShareActionProvider shareActionProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("NotifyDataAdd", "creategetBarcodeFileManager");
        setContentView(R.layout.activity_create_barcode);
        Toolbar toolbar = findViewById(R.id.toolbar_create);
        setSupportActionBar(toolbar);
        Log.d("NotifyDataAdd", "creategetBarcodeFileManager!!!!");
        imageView = findViewById(R.id.barcode_image);
        barcode_content = findViewById(R.id.text_content);
        barcode_name = findViewById(R.id.edit_text_name);
        barcode_info = findViewById(R.id.edit_text_info);
        Intent intent = getIntent();
        barcode_data = intent.getStringExtra(DATA);
        barcode_type = intent.getStringExtra(FORMAT);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.share));
        setShareActionIntent(barcode_data);
        return super.onCreateOptionsMenu(menu);
    }


    private void setShareActionIntent(String s) {
        if (s != null) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, s);
            shareActionProvider.setShareIntent(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (barcode_data != null) {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            if (BarcodeClass.isBarcodeAProduct(barcode_type)) {
                barcode_content.setText(barcode_data);
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(barcode_data, BarcodeFormat.valueOf(barcode_type), 400, 250);
                    bitmap = barcodeEncoder.createBitmap(bitMatrix);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            } else {
                barcode_content.setText(barcode_type);
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(barcode_data, BarcodeFormat.valueOf(barcode_type), 400, 400);
                    bitmap = barcodeEncoder.createBitmap(bitMatrix);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
            imageView.setImageBitmap(bitmap);

            Log.d("NotifyDataAdd", "ready");
        }
    }

    public void SaveBarcodeNewClass(View view) {
        file_saver = BarcodeFileManager.getBarcodeFileManager(this);
        String name = barcode_name.getText().toString();
        String info = barcode_info.getText().toString();
        BarcodeClass barcodeClass = new BarcodeClass(name, info, barcode_data, barcode_type, -1);
        file_saver.NotifyDataAdd(barcodeClass);
        Intent intent_jump_to_main = new Intent(this, MainActivity.class);
        startActivity(intent_jump_to_main);
    }


}
