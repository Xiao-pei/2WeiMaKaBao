package com.a1466387944.a2weimakabao;

import android.graphics.Bitmap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class BarcodeGenerator {
    public static Bitmap GenerateBarcodeBitmapFromData(String barcode_data, String barcode_type) {
        Bitmap bitmap = null;
        if (barcode_data != null) {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            if (BarcodeClass.isBarcodeAProduct(barcode_type)) {
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(barcode_data, BarcodeFormat.valueOf(barcode_type), 400, 250);
                    bitmap = barcodeEncoder.createBitmap(bitMatrix);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(barcode_data, BarcodeFormat.valueOf(barcode_type), 400, 400);
                    bitmap = barcodeEncoder.createBitmap(bitMatrix);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }
}
