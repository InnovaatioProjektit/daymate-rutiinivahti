package com.team9.daymate.viewModels;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;

import com.team9.daymate.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.team9.daymate.R;
import com.team9.daymate.adapters.CategoryAdapter;
import com.team9.daymate.adapters.RoutineAdapter;
import com.team9.daymate.core.AppDataLogic;
import com.team9.daymate.core.Presenter;
import com.team9.daymate.routines.RoutineActivity;
import com.team9.daymate.routines.RoutineObject;
import com.team9.daymate.routines.RoutinePickerFragment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class RoutineCreationViewModel extends ViewModel {

    private ArrayList<RoutineObject> routines;
    private ArrayList<RoutineObject> categories;

    private FragmentManager activity;


    public void initialize(Resources res){
        String[] resource = res.getStringArray(R.array.routine_categories);
        categories = new ArrayList<>();

        for(String cat : resource){
            categories.add(new RoutineObject(cat, 1231));
        }
    }

    public void initialize(int position, Resources res){
        String[] resource = res.getStringArray(getRoutinesFromCategory(position));
        routines = new ArrayList<>();

        for(String cat : resource){
            routines.add(new RoutineObject(cat, R.drawable.ic_sport_32));
        }
    }


    public void populateList(Context context, View view){
        ListView lv = view.findViewById(R.id.RoutineList);

        lv.setAdapter(new CategoryAdapter(context, R.layout.routine_card_category, categories));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("CATEGORY", position);

                replaceView(R.id.fragment_container, RoutinePickerFragment.class, bundle);
            }
        });
    }

    public void replaceView(int view, Class<? extends Fragment> fragmentClass, @Nullable Bundle InstanceState) {
        try {
            Constructor<? extends Fragment> fragmentInstance = fragmentClass.getDeclaredConstructor(Bundle.class);
            activity.beginTransaction().replace(view, (Fragment)fragmentInstance.newInstance(InstanceState), fragmentClass.getSimpleName()).addToBackStack((String)null).commit();
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        } catch (InstantiationException exception) {
            exception.printStackTrace();
        } catch (NoSuchMethodException exception) {
            exception.printStackTrace();
        } catch (InvocationTargetException exception) {
            exception.printStackTrace();
        }

    }

    public void populateGrid(Context context, View view){
        GridView lv = view.findViewById(R.id.RoutineGrid);

        lv.setAdapter(new RoutineAdapter(context, R.layout.routine_card_grid, routines));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppDataLogic.routines.add(routines.get(position));
                Activity activity = (Activity) context;
                Intent intent = new Intent(activity, RoutineActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("HAS_ACTIVITIES", 1);
                context.startActivity(intent);
            }
        });
    }

    public int getRoutinesFromCategory(int i){
        switch(i){
            case 0: return R.array.routine_task_food;
            case 1: return R.array.routine_task_hobby;
            case 2: return R.array.routine_task_lifestyle;
            case 3: return R.array.routine_task_sport;
            case 4: return R.array.routine_task_work;
            case 5: return R.array.routine_task_food;
            case 6: return R.array.routine_task_house;
            case 7: return R.array.routine_task_hygienia;
            case 8: return R.array.routine_task_religion;
            case 9: return R.array.routine_task_nature;
            default: return 0;
        }
    }

    public void setActivity(FragmentManager manager){
        this.activity = manager;
    }

}

