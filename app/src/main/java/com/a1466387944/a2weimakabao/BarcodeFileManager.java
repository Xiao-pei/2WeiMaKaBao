package com.a1466387944.a2weimakabao;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;


public class BarcodeFileManager implements NewBarcodeFileSaver, MainActivityBarcodeManager {
    public static String JSON_PATH = "data";
    public static String JSON_FILENAME = "data.json";
    public static String BITMAP_PATH = "bitmaps";
    private static BarcodeFileManager barcodeFileManager;
    Gson gson;
    JSONArray jsonArray = new JSONArray();
    ArrayList<BarcodeClass> barcodeClasses = null;
    Context context;

    public static BarcodeFileManager getBarcodeFileManager(Context context) {
        if (barcodeFileManager == null) {
            Log.d("barcodeManager", "create BarcodeFileManager!!");
            barcodeFileManager = new BarcodeFileManager(context);
        }
        return barcodeFileManager;
    }

    private BarcodeFileManager(Context context) {
        gson = new Gson();
        barcodeClasses = new ArrayList<>();
        this.context = context;
    }

    /*add barcodeClass to the ArrayList barcodeClasses and the jsonArray,
     * then save the changes to data/data.json */
    @Override
    public void NotifyDataAdd(BarcodeClass barcodeClass) {
        Log.d("barcodeManager", "tojson");
        String json = gson.toJson(barcodeClass);
        try {
            File file = getJsonFile();
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
    public ArrayList<BarcodeClass> getBarcodeArrayList() {
        ReadFile();
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

    /*Regenerate the jsonArray from the changed ArrayList and save it*/
    @Override
    public void NotifyDataChanged() {
        jsonArray = new JSONArray();
        try {
            for (int i = 0; i < barcodeClasses.size(); i++) {
                JSONObject json = new JSONObject(gson.toJson(barcodeClasses.get(i)));
                jsonArray.put(json);
            }
            SaveJsonArrayToFile();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void ReadFile() {
        try {
            File file = getJsonFile();
            if (file.exists()) {
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

    @Override
    public void onItemDelete(int position) {
        barcodeClasses.remove(position);
        jsonArray.remove(position);
        SaveJsonArrayToFile();
    }

    private void SaveJsonArrayToFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(getJsonFile());
            fileOutputStream.write(jsonArray.toString().getBytes());
            Log.d("barcodeManager", jsonArray.toString());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getJsonFile() {
        File dir = context.getDir(JSON_PATH, Context.MODE_PRIVATE);
        if (!dir.exists())
            dir.mkdir();
        return new File(dir, JSON_FILENAME);
    }
}
