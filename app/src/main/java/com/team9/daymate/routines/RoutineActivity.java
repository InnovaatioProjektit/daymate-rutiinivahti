package com.team9.daymate.routines;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team9.daymate.RouterActivity;
import com.team9.daymate.R;
import com.team9.daymate.core.AppDataLogic;
import com.team9.daymate.core.Presenter;
import com.team9.daymate.viewModels.NotificationViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 *
 * @author Alexander L
 */
public class RoutineActivity extends Presenter {

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        setViewModel(NotificationViewModel.class);

        getViewModel(NotificationViewModel.class).createNotificationChannel(this);
        getViewModel(NotificationViewModel.class).buildNotification(this);


        if(savedInstanceState == null){

            try {
                AppDataLogic.initialize(this);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            // Muistutus joka päivä
            int[] aika = {12,0};
            //getViewModel(NotificationViewModel.class).scheduleDailyNotification(aika, this,"MUISTA");


            this.loadView(R.id.fragment_container, RoutineDailyFragment.class, this.getIntent().getExtras());

        }


        BottomNavigationView menu = (BottomNavigationView)this.findViewById(R.id.navigation_bar);
        FloatingActionButton fab = (FloatingActionButton)this.findViewById(R.id.navigation_fab);

        Context that = this;
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadView(R.id.fragment_container, RoutineCreationFragment.class);
            }
        });

        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Bundle b = new Bundle();
                b.putInt("SELECTED", item.getItemId());
                loadActivity(RouterActivity.class, b);
                return false;
            }
        });

        fab.setImageResource(R.drawable.tab_add_56dp);
        menu.setSelectedItemId(R.id.placeholder);
    }


    @Override
    protected void onPause() {
        super.onPause();
        AppDataLogic.save(this);
    }
}