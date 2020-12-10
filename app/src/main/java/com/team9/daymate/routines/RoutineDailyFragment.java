package com.team9.daymate.routines;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.math.MathUtils;

import com.team9.daymate.R;
import com.team9.daymate.adapters.RoutineAdapter;
import com.team9.daymate.core.AppDataLogic;
import com.team9.daymate.core.UIView;
import com.team9.daymate.elements.CircularImageView;
import com.team9.daymate.example.TestViewModel;

public class RoutineDailyFragment extends UIView {
    public boolean hasLaunch;


    public RoutineDailyFragment(@Nullable Bundle InstanceState) {
        super(InstanceState, R.layout.routine_daily_fragment);  //TODO: VAIHDA LAYOUT OIKEAAN

    }

    public void onViewAction(View view) {

        GridView gv = view.findViewById(R.id.RoutineGrid);

        gv.setAdapter(new RoutineAdapter(getContext(), R.layout.routine_card_grid, AppDataLogic.routines));

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("VARASTO", "" + view.findViewById(R.id.thumbnail));

                CircularImageView cv = view.findViewById(R.id.thumbnail);
                cv.setProgressWithAnimation(cv.getProgress() + 50);

                AppDataLogic.routines.get(position).setProgress( MathUtils.clamp((cv.getProgress() + 50), 0, 100));

            }
        });


    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasLaunch = false;
        if (this.getArguments() != null) {
            hasLaunch = getArguments().getInt("HAS_ACTIVITIES") == 1;

        }
    }


}