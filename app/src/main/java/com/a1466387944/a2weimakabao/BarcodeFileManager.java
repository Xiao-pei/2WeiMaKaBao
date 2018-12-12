package com.a1466387944.a2weimakabao;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;


public class BarcodeFileManager implements NewBarcodeFileSaver, MainActivityBarcodeManager, DetailBarcodeFileManager {
    public static String JSON_PATH = "data";
    public static String JSON_FILENAME = "data.json";
    public static String CACHE_FILENAME = "cache.json";
    private static BarcodeFileManager barcodeFileManager;
    private Gson gson;
    private JSONArray jsonArray = new JSONArray();
    private ArrayList<BarcodeClass> barcodeClasses = null;
    private Context context;

    public static BarcodeFileManager getBarcodeFileManager(Context context) {
        if (barcodeFileManager == null) {
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
        String json = gson.toJson(barcodeClass);
        barcodeClasses.add(barcodeClass);
        NotifyDataChanged();
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
        Collections.sort(barcodeClasses);
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

    @Override
    public void UndoDelete() {
        try {
            File file = new File(context.getCacheDir(), CACHE_FILENAME);
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader streamReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                stringBuilder.append(inputStr);
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            NotifyDataAdd(gson.fromJson(jsonObject.toString(), BarcodeClass.class));
        } catch (IOException e) {
            e.printStackTrace();
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
    public void onItemDelete(int id) {
        for (int i = 0; i < barcodeClasses.size(); i++) {
            if (barcodeClasses.get(i).getId() == id) {
                SaveBarcodeToCache(barcodeClasses.get(i));
                barcodeClasses.remove(i);
                jsonArray.remove(i);
                break;
            }
        }
        SaveJsonArrayToFile();
    }

    @Override
    public void BarcodeDataChanged(int id, String name, String info, int expire) {
        for (int i = 0; i < barcodeClasses.size(); i++)
            if (barcodeClasses.get(i).getId() == id) {
                barcodeClasses.get(i).setInfo(info);
                barcodeClasses.get(i).setName(name);
                barcodeClasses.get(i).setExpire(expire);
                NotifyDataChanged();
                break;
            }
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

    private void SaveBarcodeToCache(BarcodeClass barcode) {
        try {
            File file = new File(context.getCacheDir(), CACHE_FILENAME);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(gson.toJson(barcode).getBytes());
            fileOutputStream.close();
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
