package com.team9.daymate.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.team9.daymate.R;
import com.team9.daymate.core.UIView;
import com.team9.daymate.routines.RoutineViewModel;

public class ProgressFragment extends UIView {

    public ProgressFragment(@Nullable Bundle InstanceState) {
        super(InstanceState, R.layout.progress_fragment);
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
