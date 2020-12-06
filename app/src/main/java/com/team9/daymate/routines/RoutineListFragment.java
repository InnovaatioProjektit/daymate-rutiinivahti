package com.team9.daymate.routines;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.team9.daymate.R;
import com.team9.daymate.core.UIView;

public class RoutineListFragment extends UIView {

    public RoutineListFragment(@Nullable Bundle InstanceState) {
        super(InstanceState, R.layout.routine_list_fragment);
    }

    public void onViewAction(View view) {

    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null) {
        }

        this.setViewModel(RoutineViewModel.class);
    }
}
