package com.team9.daymate.core;

import com.team9.daymate.routines.RoutineObject;

import java.util.ArrayList;

public class AppDataLogic {
    public static final JsonContainerFactory localCache = new JsonContainerFactory();
    public static ArrayList<RoutineObject> routines = new ArrayList<RoutineObject>();

    public static String CHANNEL_ID ="JokuId";

    private AppDataLogic() {



    }
}
