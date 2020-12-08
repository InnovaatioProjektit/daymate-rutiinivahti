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

public class RoutineDailyFragment extends UIView {


    public RoutineDailyFragment(@Nullable Bundle InstanceState) {
        super(InstanceState, R.layout.test_activity);
    }

    public void onViewAction(View view) {

        CircularImageView civ = getView().findViewById(R.id.circle);

        civ.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                civ.setProgressWithAnimation(civ.getProgress() + 25f);

            }
        });

        civ.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener(){

            @Override
            public void onViewAttachedToWindow(View v) {
                v.removeOnAttachStateChangeListener(this);
                //circularHideCard(civ, 10, 10);



            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });


    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null) {
        }
    }


}