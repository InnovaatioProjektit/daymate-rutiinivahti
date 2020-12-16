package com.team9.daymate.viewModels;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import androidx.lifecycle.ViewModel;

import com.team9.daymate.R;
import com.team9.daymate.adapters.CategoryAdapter;
import com.team9.daymate.adapters.RoutineAdapter;
import com.team9.daymate.core.AppDataLogic;
import com.team9.daymate.core.RoutineObject;

import java.util.ArrayList;

public class RoutineViewModel extends ViewModel {

    private ArrayList<RoutineObject> categories;
    private ArrayList<RoutineObject> routines;

    private RoutineObject sharedData;

    // Kategorian valinta
    private int selection = 0;

    public void setCategories(Resources res){
        String[] resource;
        if(categories == null){
            resource = res.getStringArray(R.array.routine_categories);
            categories = new ArrayList<RoutineObject>();

            int i = 0;
            for(String category : resource){
               // RoutineObject r = new RoutineObject(category, 1231);
                categories.add(new RoutineObject(category, 0, getIconColor(i++),0));
            }
        }
    }

    private void setRoutines(Resources res){
        String[] resource;
        int[] themes = {};

        resource = res.getStringArray(getRoutinesFromCategory(selection));


        routines = new ArrayList<RoutineObject>();

        for(String routine : resource){
            routines.add(new RoutineObject(routine, getIconResource(routine), getIconColor(selection), 0));
        }

    }

    public ListView populateCategoryList(Context context, View view){
        ListView lv = view.findViewById(R.id.RoutineList);
        lv.setAdapter(new CategoryAdapter(context, R.layout.routine_card_category, categories));
        return lv;
    }

    public GridView populateRoutineGrid(Context context, View view){
        GridView gv = view.findViewById(R.id.RoutineGrid);

        setRoutines(context.getResources());
        gv.setAdapter(new RoutineAdapter(context, R.layout.routine_card_grid, routines));
        return gv;
    }

    public GridView populateAppGrid(Context context, View view){
        GridView gv = view.findViewById(R.id.RoutineGrid);
        gv.setAdapter(new RoutineAdapter(context, R.layout.routine_card_grid, AppDataLogic.routines));
        return gv;
    }

    public void selectCategory(int categorySelection){
        selection = categorySelection;
    }

    public RoutineObject getCategory(int position){
        return routines.get(position);
    }

    public int getRoutinesFromCategory(int i){
        switch(i){
            case 0: return R.array.routine_task_food;
            case 1: return R.array.routine_task_hobby;
            case 2: return R.array.routine_task_lifestyle;
            case 3: return R.array.routine_task_sport;
            case 4: return R.array.routine_task_work;
            case 5: return R.array.routine_task_house;
            case 6: return R.array.routine_task_hygienia;
            case 7: return R.array.routine_task_religion;
            case 8: return R.array.routine_task_nature;
            case 9: return R.array.routine_task_health;
            default: return 0;
        }
    }

    public RoutineObject sendToEditFragment(int position){
        return routines.get(position);
    }

    public RoutineObject getShaderEditData(){
        return sharedData;
    }

    private int getIconResource(String id){
        switch(id){
            case "Kahvitauko": return R.drawable.ic_tea_24;
            case "Brunssi": return R.drawable.ic_mealhouse_24;
            case "Snacktime":
            case "Päivällinen":
            case "Aamiainen":
            case "Illallinen":
            case "Ruuanlaitto":
            case "Syöminen":
                return R.drawable.ic_food_24;
            case "Vesi": return R.drawable.ic_drink_24;
            case "Patikointi":
            case "Ulkoilutus": return R.drawable.ic_walk_24;
            case "Soittovälineet":
            case "Musiikki":
            case "Laulaminen": return R.drawable.ic_music_24;
            case "Pelit": return R.drawable.ic_game_24;
            case "Televisio": return R.drawable.ic_tv_24;
            case "Herätys": return R.drawable.ic_alarm_24;
            case "Zen hetki":
            case "Rentoutuminen":
            case "Oma aika":
            case "Hiljainen hetki":
            case "Jooga": return R.drawable.ic_calm_24;
            case "Nukkuminen": return R.drawable.ic_sleep_24;
            case "Perheaika": return R.drawable.ic_family_24;
            case "Rakkaus": return R.drawable.ic_love_24;
            case "Keskustelu":  return R.drawable.ic_chat_24;
            case "Liikuntasali":
            case "Kiipeily":
            case "Voimistelu": return R.drawable.ic_sport_32;
            case "Kamppailulaji": return R.drawable.ic_ufc_24;
            case "Maksumuistutus": return R.drawable.ic_pay_24;
            case "Koe": return R.drawable.ic_school_24;
            case "Projekti":
            case "Salkku":
            case "Työpäivä": return R.drawable.ic_work_24;
            case "Siivoa":
            case "Pese":  return R.drawable.ic_wash_24;
            case "Suihku": return R.drawable.ic_washroom_24;
            case "Lemmikki": return R.drawable.ic_pet_24;
            case "Hampaiden harjaus": return R.drawable.ic_smile_24;
            case "Sauna": return R.drawable.ic_sauna_24;
            case "Meikkaus": return R.drawable.ic_face_24;
            case "Hiustenleikkaus": return R.drawable.ic_cut_24;
            case "Rukoilu":
            case "Kirkkotapahtuma":
                return R.drawable.ic_pray_24;
            case "Telttailu":
            case "Kalastus":
                return R.drawable.ic_mountian_24;
            case "Lääkemuistutus":
            case "Lääkärinkäynti":
            case "Hammashoitokäynti":
            case "Verenpaine":
                return R.drawable.ic_medicine_24;
        }

        return R.drawable.tab_routines_24dp;
    }

    private int getIconColor(int selection){
        switch(selection){
            case 0: return Color.parseColor("#EDAA33");   // carrot
            case 1: return Color.parseColor("#E2A953");
            case 2: return Color.parseColor("#72A1E7");
            case 3: return Color.parseColor("#AABD00");
            case 4: return Color.parseColor("#A9B5B7"); // grey
            case 5: return Color.parseColor("#87E1EB");  // cyan
            case 6: return Color.parseColor("#A9C6F1");  // blue
            case 7: return Color.parseColor("#9D4D74");
            case 8: return Color.parseColor("#80ea8a");
            case 9: return Color.parseColor("#03dac5");
        }

        return Color.TRANSPARENT;
    }

}