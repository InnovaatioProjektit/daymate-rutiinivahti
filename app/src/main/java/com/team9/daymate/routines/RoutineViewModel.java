package com.team9.daymate.routines;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import androidx.lifecycle.ViewModel;

import com.team9.daymate.R;
import com.team9.daymate.adapters.RoutineAdapter;
import com.team9.daymate.core.AppDataLogic;

import java.util.ArrayList;

public class RoutineViewModel extends ViewModel {

    private ArrayList<RoutineObject> routines;


    public void initialize(){
        routines = AppDataLogic.routines;


    }


    public void populateList(Context context, View view){
        ListView lv = view.findViewById(R.id.RoutineList);

        lv.setAdapter(new RoutineAdapter(context, R.layout.routine_card_mini, routines));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public void populateGrid(Context context, View view){
        GridView lv = view.findViewById(R.id.RoutineGrid);

        lv.setAdapter(new RoutineAdapter(context, R.layout.routine_card_grid, routines));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

}
