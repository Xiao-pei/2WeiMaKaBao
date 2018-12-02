package com.a1466387944.a2weimakabao;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BarcodeFileManager implements NewBarcodeFileSaver, MainActivityBarcodeManager {
    public static String JSON_PATH = "data";
    public static String JSON_FILENAME = "data.json";
    public static String BITMAP_PATH = "bitmaps";
    private static BarcodeFileManager barcodeFileManager;
    Gson gson;
    JSONArray jsonArray = new JSONArray();
    ArrayList<BarcodeClass> barcodeClasses = null;

    public static BarcodeFileManager getBarcodeFileManager() {
        if (barcodeFileManager == null) {
            Log.d("barcodeManager", "create BarcodeFileManager!!");
            barcodeFileManager = new BarcodeFileManager();
        }
        return barcodeFileManager;
    }

    private BarcodeFileManager() {
        gson = new Gson();
        barcodeClasses = new ArrayList<>();
    }

    @Override
    public void NotifyDataAdd(BarcodeClass barcodeClass, Context context) {
        Log.d("barcodeManager", "tojson");
        String json = gson.toJson(barcodeClass);
        try {
            File dir = context.getDir(JSON_PATH, Context.MODE_PRIVATE);
            if (!dir.exists())
                dir.mkdir();
            File file = new File(dir, JSON_FILENAME);
            Log.d("barcodeManager", "File get!");
            barcodeClasses.add(barcodeClass);
            jsonArray.put(new JSONObject(json));
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(jsonArray.toString().getBytes());
            Log.d("barcodeManager", jsonArray.toString());
            fileOutputStream.close();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<BarcodeClass> getBarcodeArraryList(Context context) {
        ReadFile(context);
        barcodeClasses = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                barcodeClasses.add(gson.fromJson(jsonArray.getString(i), BarcodeClass.class));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return barcodeClasses;
    }

    private void ReadFile(Context context) {
        try {
            File dir = context.getDir(JSON_PATH, Context.MODE_PRIVATE);
            File file = new File(dir, JSON_FILENAME);
            if (file.exists()) {
                Log.d("barcodeManager", "read from file!");
                FileInputStream inputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader streamReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    stringBuilder.append(inputStr);
                jsonArray = new JSONArray(stringBuilder.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    barcodeClasses.add(gson.fromJson(jsonArray.getString(i), BarcodeClass.class));
                }
                inputStreamReader.close();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
