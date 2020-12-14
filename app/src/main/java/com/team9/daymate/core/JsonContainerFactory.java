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

/**
 * Alustaa ja säilyttää json-muotoisen tiedoston sovelluksen sisäisessä muistissa.
 *
 * @author Alex L (ryhmä 9)
 * @author Kirill Keränen (ryhmä 7)
 */
public class JsonContainerFactory {
    private static final String FILE_IDENT = "UNIQUE_APP_LOCALSTORAGE_KEY";
    private JSONObject json_cache;

    public JsonContainerFactory() {
        Log.d("REPOSITORY", "hi");
    }

    /**
     * Poistaa koko JSON tiedoston
     *
     * @param context Sovellusympäristön käyttöliittymä
     */
    public void purge(Context context) {
        context.deleteFile(FILE_IDENT);
    }

    /**
     * Poistaa alkion tiedostosta
     *
     * @param name Sovellusympäristön käyttöliittymä
     */
    public void remove(String name) {
        this.json_cache.remove(name);
    }


    /**
     * Lisää alkion nimisen tiedon JSONObjektina tietorakenteseen
     *
     * @param name Alkion nimi
     * @param item Alkion objekti
     * @return
     */
    public static JSONObject Set(String name, Object item) {
        JSONObject jsob = new JSONObject();
        try {
            jsob.putOpt(name, item);
        } catch (JSONException jex) {
            jex.printStackTrace();
        }

        return jsob;
    }


    /**
     *  Lukee sisäisestä muistista JSON-muotoisen tiedoston. Mikäli sellainen puuttuu,
     *  alustaa uuden tiedoston.
     *
     * @param _context Sovellusympäristön käyttöliittymä
     */
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


    /**
     * Palauttaa tietorakenteesta alkion
     *
     * @param name Alkion nimi
     * @return Object tietotyypin alkio
     */
    public Object get(String name) {
        return this.json_cache.opt(name);
    }


    /**
     * Tuodaan JSONArrayn alkiot tyhjään listaan
     *
     * @param name Alkion nimi
     * @return list tyhjä alustettu lista
     */
    public <T> void get(String name, ArrayList<T> list) throws JSONException {
        JSONArray jar = json_cache.getJSONArray(name);

        for(int i=0;i<jar.length(); i++){
            list.add((T)jar.get(i));
        }
    }


    /**
     * Lisää tietotyypin alkion JSON-tietorakenteseen
     *
     * @param name Alkion nimi
     * @param item Tallennettava alkio
     */
    public void add(String name, Object item) {
        try {
            this.json_cache.putOpt(name, item);
        } catch (JSONException jex) {
            jex.printStackTrace();
        }
    }


    /**
     * Konvertoi tyypillisen listan {@see JSONArray} listaksi ja lisää tietorakenteseen
     *
     * @param name Muunnetun tietotyypin nimi
     * @param list Lista joka muunnetaan
     * @param <T>  Generinen listan alkio
     */
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

    /**
     * Tallentaa tietorakenne JSON-tyyppisen tiedostoon puhelimen sisäiseen muistiin
     *
     * @param context  Sovellusympäristön käyttöliittymä
     */
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
