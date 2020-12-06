package com.team9.daymate.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team9.daymate.R;
import com.team9.daymate.core.Presenter;
import com.team9.daymate.example.TestFragment;
import com.team9.daymate.example.TestViewModel;
import com.team9.daymate.fragments.ProgressFragment;
import com.team9.daymate.routines.RoutineActivity;
import com.team9.daymate.routines.RoutineListFragment;
import com.team9.daymate.routines.RoutineViewModel;


public class ShopActivity extends Presenter {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main_full);
        this.setViewModel(TestViewModel.class);

        if (savedInstanceState == null) {
            this.loadView(R.id.fragment_container, ShopStoreFragment.class, this.getIntent().getExtras());
        }

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
                        setViewModel(RoutineViewModel.class);
                        replaceView(R.id.fragment_container, RoutineListFragment.class);
                        break;
                    case R.id.navigation_tab_shop:
                        setViewModel(ShopViewModel.class);
                        loadActivity(ShopActivity.class);
                }

                return true;
            }
        });
    }
}