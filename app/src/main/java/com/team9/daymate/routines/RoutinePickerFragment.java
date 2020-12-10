package com.team9.daymate.routines;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.team9.daymate.R;
import com.team9.daymate.core.UIView;
import com.team9.daymate.viewModels.RoutineCreationViewModel;

public class RoutinePickerFragment extends UIView {

    public RoutinePickerFragment(@Nullable Bundle InstanceState) {
        super(InstanceState, R.layout.routine_grid_fragment);
    }

    public void onViewAction(View view) {
        getViewModel(RoutineCreationViewModel.class).populateGrid(getContext(), view);
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null) {
        }

        this.setViewModel(RoutineCreationViewModel.class);
        getViewModel(RoutineCreationViewModel.class).initialize(getArguments().getInt("CATEGORY"), getResources());
    }
}
