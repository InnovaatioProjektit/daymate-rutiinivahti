package com.team9.daymate.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.team9.daymate.R;
import com.team9.daymate.core.AppDataLogic;
import com.team9.daymate.core.UIView;
import com.team9.daymate.elements.CircularImageView;
import com.team9.daymate.elements.ProgressPillar;
import com.team9.daymate.viewModels.NotificationViewModel;
import com.team9.daymate.viewModels.UserViewModel;


/**
 *
 * @author Alexander L
 */
public class ProgressFragment extends UIView {

    public ProgressFragment(@Nullable Bundle InstanceState) {
        super(InstanceState, R.layout.progress_fragment);
    }

    public void onViewAction(View view) {

        ProgressPillar progress_sunday = view.findViewById(R.id.progressbar_sunday);
        ProgressPillar progress_monday = view.findViewById(R.id.progressbar_monday);
        ProgressPillar progress_tuesday = view.findViewById(R.id.progressbar_tuesday);
        ProgressPillar progress_wednesday = view.findViewById(R.id.progressbar_wednesday);
        ProgressPillar progress_thursday = view.findViewById(R.id.progressbar_thursday);
        ProgressPillar progress_friday = view.findViewById(R.id.progressbar_friday);
        ProgressPillar progress_saturday =  view.findViewById(R.id.progressbar_saturday);


        progress_sunday.setProgressWithAnimation((AppDataLogic.weekly_points[0] / AppDataLogic.target) * 100);
        progress_monday.setProgressWithAnimation((AppDataLogic.weekly_points[1] / AppDataLogic.target) * 100);
        progress_tuesday.setProgressWithAnimation((AppDataLogic.weekly_points[2] / AppDataLogic.target) * 100);
        progress_wednesday.setProgressWithAnimation((AppDataLogic.weekly_points[3] / AppDataLogic.target) * 100);
        progress_thursday.setProgressWithAnimation((AppDataLogic.weekly_points[4] / AppDataLogic.target) * 100);
        progress_friday.setProgressWithAnimation((AppDataLogic.weekly_points[5] / AppDataLogic.target) * 100);
        progress_saturday.setProgressWithAnimation((AppDataLogic.weekly_points[6] / AppDataLogic.target) * 100);


        CircularImageView cv = view.findViewById(R.id.progress_circularView);
        cv.setProgressWithAnimation((AppDataLogic.weekly_points[AppDataLogic.today - 1] / AppDataLogic.target) * 100);


        ((TextView)view.findViewById(R.id.progress_creditView)).setText("" + AppDataLogic.weekly_points[AppDataLogic.today - 1]);





    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null) {
        }

        this.setViewModel(UserViewModel.class);
    }
}
