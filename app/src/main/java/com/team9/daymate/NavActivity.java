package com.team9.daymate;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team9.daymate.core.Presenter;
import com.team9.daymate.example.TestViewModel;
import com.team9.daymate.fragments.ProgressFragment;
import com.team9.daymate.routines.RoutineActivity;
import com.team9.daymate.routines.RoutineDailyFragment;
import com.team9.daymate.routines.RoutineListFragment;
import com.team9.daymate.shop.ShopActivity;


public class NavActivity extends Presenter {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main_full);


        BottomNavigationView menu = (BottomNavigationView)this.findViewById(R.id.navigation_bar);
        FloatingActionButton fab = (FloatingActionButton)this.findViewById(R.id.navigation_fab);

        @IdRes int res = getIntent().getExtras().getInt("SELECTED");
        Class<? extends Fragment> fragment;
        if((fragment = getFragmentFromResources(res)) != null){
            loadView(R.id.fragment_container, fragment);
        }



        menu.setSelectedItemId(res);

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadActivity(RoutineActivity.class);
            }
        });

        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

               Class<? extends Fragment> cls = getFragmentFromResources(item.getItemId());

               if(cls != null){
                   replaceView(R.id.fragment_container, cls);
                   return true;
               }
                return false;
            }
        });
    }


    @Nullable
    private Class<? extends Fragment> getFragmentFromResources(@IdRes @NonNull  int resourceID){
        switch(resourceID) {
            case R.id.navigation_tab_progress: return ProgressFragment.class;
            case R.id.navigation_tab_routines: return RoutineListFragment.class;
            case R.id.navigation_tab_shop:
                loadActivity(ShopActivity.class);
                return null;
            default: return null;
        }
    }



}