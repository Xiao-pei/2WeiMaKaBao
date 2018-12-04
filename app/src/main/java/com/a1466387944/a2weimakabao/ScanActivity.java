package com.a1466387944.a2weimakabao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("barcodeManager", "准备启动扫描");
        new IntentIntegrator(this).initiateScan(IntentIntegrator.ALL_CODE_TYPES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("barcodeManager", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                Intent to_main = new Intent(this, MainActivity.class);
                this.finish();
                startActivity(to_main);
            } else {
                Log.d("barcodeManager", "Scanned: " + result.getContents() + result.getFormatName());
                Intent to_create = new Intent(this, CreateBarcodeActivity.class);
                to_create.putExtra(CreateBarcodeActivity.DATA, result.getContents());
                to_create.putExtra(CreateBarcodeActivity.FORMAT, result.getFormatName());
                this.finish();
                startActivity(to_create);
            }
        }
    }

}
