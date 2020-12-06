package com.team9.daymate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

        fab.setImageResource(R.drawable.tab_bullseye_56dp);

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadActivity(RoutineActivity.class);
            }
        });

        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {
                    case R.id.navigation_tab_progress:
                        //setViewModel(ProgressViewModel.class);
                        replaceView(R.id.fragment_container, ProgressFragment.class);
                        break;
                    case R.id.navigation_tab_routines:
                        replaceView(R.id.fragment_container, RoutineListFragment.class);
                        break;
                    case R.id.navigation_tab_shop:
                        loadActivity(ShopActivity.class);
                        break;
                    default: return false;
                }

                return true;
            }
        });

        menu.setSelectedItemId(getIntent().getExtras().getInt("SELECTED"));
    }
}