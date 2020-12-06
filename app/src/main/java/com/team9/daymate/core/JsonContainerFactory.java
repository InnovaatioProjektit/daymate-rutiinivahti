package com.team9.daymate.core;


import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


//TODO: KIRILLILTÄ ALKUPERÄINEN
public class JsonContainerFactory {
    private static final String FILE_IDENT = "UNIQUE_APP_LOCALSTORAGE_KEY";
    private JSONObject json_cache;

    public JsonContainerFactory() {
        Log.d("REPOSITORY", "hi");
    }

    public void purge(Context context) {
        context.deleteFile("UNIQUE_APP_LOCALSTORAGE_KEY");
    }

    public void remove(String name) {
        this.json_cache.remove(name);
    }

    public static JSONObject Set(String name, Object item) {
        JSONObject jsob = new JSONObject();

        try {
            jsob.putOpt(name, item);
        } catch (JSONException jex) {
            jex.printStackTrace();
        }

        return jsob;
    }

    public static JSONArray Array(ArrayList list) {
        JSONArray jsar = new JSONArray();
        for(Object item : list){
            jsar.put(item);
        }

        return jsar;
    }

    public static JSONArray Array(String list) {
        JSONArray jsar = new JSONArray();
        String[] var2 = list.split(";");
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Object i = var2[var4];
            jsar.put(i);
        }

        return jsar;
    }

    public void initialize(Context _context) {
        File internal_filepath = _context.getFilesDir();
        File fileref = new File(internal_filepath, "UNIQUE_APP_LOCALSTORAGE_KEY");

        try {
            if (fileref.exists()) {
                InputStream reader = new FileInputStream(fileref);
                byte[] content = new byte[reader.available()];
                reader.read(content);
                reader.close();

                try {
                    this.json_cache = new JSONObject(new String(content, StandardCharsets.UTF_8));
                } catch (JSONException var7) {
                    Log.w("InternalStorage", "Error parsing to JSONObject ", var7);
                }
            } else {
                OutputStream writer = new FileOutputStream(fileref);
                writer.write("{}".getBytes());
                writer.close();
                this.json_cache = new JSONObject();
            }
        } catch (IOException ioex) {
            Log.w("InternalStorage", "Error writing " + fileref, ioex);
        }

    }

    public Object get(String name) {
        return this.json_cache.opt(name);
    }

    public void add(String name, Object item) {
        try {
            this.json_cache.putOpt(name, item);
        } catch (JSONException var4) {
            var4.printStackTrace();
        }

    }

    public void commit(Context context) {
        File internal_filepath = context.getFilesDir();
        File fileref = new File(internal_filepath, "UNIQUE_APP_LOCALSTORAGE_KEY");

        try {
            OutputStream writer = new FileOutputStream(fileref);
            writer.write(this.json_cache.toString().getBytes());
        } catch (IOException var5) {
            Log.w("InternalStorage", "Error writing " + fileref, var5);
        }

    }
}
