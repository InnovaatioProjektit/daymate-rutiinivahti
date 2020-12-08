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


public class JsonContainerFactory {
    private static final String FILE_IDENT = "UNIQUE_APP_LOCALSTORAGE_KEY";
    private JSONObject json_cache;

    public JsonContainerFactory() {
        Log.d("REPOSITORY", "hi");
    }

    public void purge(Context context) {
        context.deleteFile(FILE_IDENT);
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

    public void initialize(Context _context) {
        File internal_filepath = _context.getFilesDir();
        File fileref = new File(internal_filepath, FILE_IDENT);

        try {
            if (fileref.exists()) {
                InputStream reader = new FileInputStream(fileref);
                byte[] content = new byte[reader.available()];
                reader.read(content);
                reader.close();

                try {
                    this.json_cache = new JSONObject(new String(content, StandardCharsets.UTF_8));
                } catch (JSONException ex) {
                    Log.w("InternalStorage", "Error parsing to JSONObject ", ex);
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
        } catch (JSONException jex) {
            jex.printStackTrace();
        }
    }

    public <T> void add(String name, ArrayList<T> list){
        JSONArray jar = new JSONArray();

        for(T item : list){
            jar.put(item);
        }

        try{
            json_cache.putOpt(name, jar);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void commit(Context context) {
        File internal_filepath = context.getFilesDir();
        File fileref = new File(internal_filepath, FILE_IDENT);

        try {
            OutputStream writer = new FileOutputStream(fileref);
            writer.write(this.json_cache.toString().getBytes());
        } catch (IOException var5) {
            Log.w("InternalStorage", "Error writing " + fileref, var5);
        }

    }
}
