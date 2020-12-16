package com.team9.daymate.core;

import android.content.Context;
import android.icu.util.Calendar;
import android.text.format.DateFormat;
import android.util.JsonWriter;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.team9.daymate.routines.RoutineActivity;
import com.team9.daymate.viewModels.RoutineEditViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;


/**
 * Sovelluksen ylin (globaali) logiikka luokka
 *
 * @author Alexander L
 */
public class AppDataLogic {
    public static final JsonContainerFactory localCache = new JsonContainerFactory();
    public static ArrayList<RoutineObject> routines = new ArrayList<RoutineObject>();
    public static ArrayList<Object> allRoutines;

    // Tämän hetkinen viikon päivä (sunnuntai = 1, maanantai = 2, lauantai = 7
    public static final int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);


    // Rutiinien viikkokatsaus ( sunnuntai = 1)
    public static int[] weekly_points;

    // montako rutiinia pitää suorittaa
    public static float target = 0;

    private static boolean init;


    public static void initialize(Context context) throws JSONException {
        // TODO: JOS ALKAA MÄÄTTÄÄ, PURGE TÄÄLLÄ
        //localCache.purge(context);
        if (localCache.initialize(context)) {
            // Uusi tiedosto
            localCache.add("date", 0);
            localCache.add("points", new int[]{0, 0, 0, 0, 0, 0, 0});
            localCache.add("routines", new JSONArray());
            localCache.commit(context);
        }

        if (init) return;
        init = true;

        weekly_points = new int[7];
        localCache.get("points", weekly_points);

        // TODO: Ei ehkä otollisinta hakea KAIKKI rutiinit käsittelyyn

        allRoutines = new ArrayList<Object>();

        localCache.get("routines", allRoutines);

        int i = 0;
        for(Object encoded : allRoutines){
            RoutineObject rout = RoutineObject.FromJSON((JSONObject) encoded);

            // Otetaan vain tämän päivän rutiinit käyttöön
            if(rout.getFlags().contains(RoutineEditViewModel.FLAGS.valueOf(todayToString()))){
                target++;
                routines.add(rout);
            }
        }

        // Päivä vaihtui?
        if ((int) localCache.get("date") != today) {
            localCache.add("date", today);

            // putsataan päivän rutiinit
            for(RoutineObject routine : routines){
                routine.reset();
            }

            if(today == 6){
                // maanantai, putsataan viikkonäkymä
                weekly_points = new int[]{0, 0, 0, 0, 0, 0, 0};
            }
        }
    }

    public static String todayToString(){
        switch (today){
            case 1: return "SUNDAY";
            case 2: return "MONDAY";
            case 3: return "TUESDAY";
            case 4: return "WEDNESDAY";
            case 5: return "THURSDAY";
            case 6: return "FRIDAY";
            case 7: return "SATURDAY";
        }
        return "NONE";
    }

    public static void save(Context context){
        localCache.add("points", weekly_points);


        for(RoutineObject roob : routines){
            edit("routines", roob);
        }

        localCache.verbose();
        localCache.commit(context);
    }

    /**
     * Yrittää löytää alkion JSON osiosta ja muokata se, jos ei löydy vertaista,
     * niin lisätään listan loppuun.
     *
     * @param name JSON osion nimi
     * @param item Objekti jota lisätään tai muokataan
     */
    public static void edit(String name, RoutineObject item){
        try {
            JSONArray jar = (JSONArray) localCache.get(name);

            int len = jar.length();
            for(int i = 0; i < len; i++){
                RoutineObject roob = RoutineObject.FromJSON((JSONObject) jar.get(i));



                if(item.uuid.equals(roob.uuid)){
                    //Log.d("VARASTO", "POISTO: " + item.getTitle());
                    // listasta löyty saman alkion vanha versio, poistetaan vanha ja lisätään muutokset
                    jar.put(i, item.toJSON());
                    return;
                }
            }

            Log.d("VARASTO","LISÄYS: " + item.getTitle());
            jar.put(item.toJSON());

        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage());
            e.printStackTrace();
        }
    }

}
