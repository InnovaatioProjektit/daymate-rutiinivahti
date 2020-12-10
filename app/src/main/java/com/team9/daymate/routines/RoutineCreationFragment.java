package com.team9.daymate.routines;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.team9.daymate.R;
import com.team9.daymate.core.UIView;
import com.team9.daymate.elements.CircularImageView;
import com.team9.daymate.example.TestViewModel;
import com.team9.daymate.viewModels.RoutineCreationViewModel;

public class RoutineCreationFragment extends UIView {

    public RoutineCreationFragment(@Nullable Bundle InstanceState) {
        super(InstanceState, R.layout.routine_category_fragment);
    }

    public void onViewAction(View view) {
        getViewModel(RoutineCreationViewModel.class).populateList(getContext(), view);
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null) {
        }

        this.setViewModel(RoutineCreationViewModel.class);
        getViewModel(RoutineCreationViewModel.class).setActivity(getFragmentManager());
        getViewModel(RoutineCreationViewModel.class).initialize(getResources());
    }


}
