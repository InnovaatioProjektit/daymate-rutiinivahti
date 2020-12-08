package com.team9.daymate.routines;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team9.daymate.NavActivity;
import com.team9.daymate.R;
import com.team9.daymate.core.Presenter;
import com.team9.daymate.example.TestViewModel;
import com.team9.daymate.fragments.ProgressFragment;
import com.team9.daymate.shop.ShopActivity;

public class RoutineActivity extends Presenter {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.setViewModel(TestViewModel.class);

        if (savedInstanceState == null) {
            this.loadView(R.id.fragment_container, RoutineDailyFragment.class, this.getIntent().getExtras());

        }

        BottomNavigationView menu = (BottomNavigationView)this.findViewById(R.id.navigation_bar);
        FloatingActionButton fab = (FloatingActionButton)this.findViewById(R.id.navigation_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //TODO LUO RUTIINI (KATEGORIA -> RUTIINIVALIKKO -> UUSI RUTIINI -> PÄIVÄN LOKI) Fragmentit [UIVIEW]
            }
        });

        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Bundle saveInstance = new Bundle();

                switch(item.getItemId()) {
                    case R.id.navigation_tab_progress: break;
                    case R.id.navigation_tab_routines: break;
                    case R.id.navigation_tab_shop:
                        loadActivity(ShopActivity.class);
                        return true;
                    default: return false;
                }

                saveInstance.putInt("SELECTED", item.getItemId());
                loadActivity(NavActivity.class, saveInstance);

                return false;
            }
        });

        fab.setImageResource(R.drawable.tab_add_56dp);
        menu.setSelectedItemId(R.id.placeholder);
    }
}