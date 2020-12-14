package com.team9.daymate.viewModels;

import android.content.Context;
import android.content.res.Resources;
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

            for(String category : resource){
                RoutineObject r = new RoutineObject(category, 1231);
                categories.add(new RoutineObject(category, 1231));
            }
        }
    }

    private void setRoutines(Resources res){
        String[] resource;

        resource = res.getStringArray(getRoutinesFromCategory(selection));
        routines = new ArrayList<RoutineObject>();

        for(String routine : resource){
            routines.add(new RoutineObject(routine, R.drawable.ic_sport_32));
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
        Log.d("VARASTO", i + "");
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

}