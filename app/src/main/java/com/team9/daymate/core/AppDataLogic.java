package com.team9.daymate.core;

import android.content.Context;

import com.team9.daymate.routines.RoutineActivity;

import org.json.JSONException;

import java.util.ArrayList;


/**
 * Sovelluksen ylin (globaali) logiikka luokka
 *
 * @author Alexander L
 */
public class AppDataLogic {
    public static final JsonContainerFactory localCache = new JsonContainerFactory();
    public static ArrayList<RoutineObject> routines = new ArrayList<RoutineObject>();


    public static int points;
    public static int morningRoutines;
    public static int eveningRoutines;
    public static int dailyRoutines;


    public static void initialize(Context context) throws JSONException {
        localCache.initialize(context);

        points = (int)localCache.get("points");
        dailyRoutines = (int)localCache.get("dailyRoutines");
        morningRoutines = (int)localCache.get("morningRoutines");
        eveningRoutines = (int)localCache.get("eveningRoutines");

        localCache.get("routines", routines);

    }

    public static void save(Context context){
        localCache.add("points", points);
        localCache.add("dailyRoutines", dailyRoutines);
        localCache.add("morningRoutines", morningRoutines);
        localCache.add("eveningRoutines", eveningRoutines);
        localCache.add("routines", routines);
        localCache.commit(context);
    }






    public static String CHANNEL_ID ="Daymate Hello";
}
